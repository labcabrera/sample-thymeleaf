package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.CountryEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers.CountryEntityMapper;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.labcabrera.sample.api.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.api.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountryRepositoryJpaAdapter implements CountryRepository {

    private final CountryJpaRepository jpaRepository;
    private final CountryEntityMapper mapper;
    private final RSQLParser rsqlParser;

    @Override
    @Cacheable(value = "country", key = "#p0", unless = "#result == null || #result.isEmpty()")
    public Optional<Country> findById(String countryId) {
        return jpaRepository.findById(countryId).map(mapper::toDomain);
    }

    @Override
    public Optional<Country> findByIdOrName(String id, String name) {
        return jpaRepository.findByIdAndNameIgnoreCase(id, name).map(mapper::toDomain);
    }

    @Override
    public Page<Country> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user) {
        Specification<CountryEntity> authSpec = (root, query, cb) -> {
            return cb.conjunction();
        };
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(authSpec, pageable);
            return page.map(mapper::toDomain);
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<CountryEntity> spec = rootNode.accept(new CustomRsqlVisitor<CountryEntity>());
            Specification<CountryEntity> finalSpec = (spec == null) ? authSpec : spec.and(authSpec);
            var page = jpaRepository.findAll(finalSpec, pageable);
            return page.map(mapper::toDomain);
        }
        catch (Exception ex) {
            throw new BadRequestException("rsql.msg.err.parse", ex, rsql);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "country", key = "#result.id")
    public Country save(Country country) {
        try {
            if (country.getId() != null && jpaRepository.existsById(country.getId())) {
                throw new BadRequestException("country.msg.err.already-exists", country.getId());
            }
            var entity = mapper.toEntity(country);
            var savedEntity = jpaRepository.save(entity);
            return mapper.toDomain(savedEntity);
        }
        catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("country.msg.err.data-integrity", ex);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "country", key = "#p0")
    public Country update(String countryId, Country updated) {
        CountryEntity current = jpaRepository.findById(countryId)
            .orElseThrow(() -> new BadRequestException("Country not found with id " + countryId));
        current.setName(updated.getName());
        current.setUpdatedAt(updated.getUpdatedAt() != null ? updated.getUpdatedAt() : LocalDateTime.now());
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "country", key = "#p0")
    public void deleteById(String countryId) {
        jpaRepository.deleteById(countryId);
    }

}
