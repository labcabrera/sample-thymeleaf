package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.MunicipalityEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers.MunicipalityEntityMapper;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.labcabrera.sample.api.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.api.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class MunicipalityRepositoryJpaAdapter implements MunicipalityRepository {

    private final MunicipalityJpaRepository jpaRepository;
    private final MunicipalityEntityMapper mapper;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<Municipality> findByName(String name) {
        if (name != null && !name.isBlank()) {
            var e = jpaRepository.findByNameIgnoreCase(name);
            if (e.isPresent()) {
                return e.map(mapper::toDomain);
            }
        }
        return Optional.empty();
    }

    @Override
    @Cacheable(value = "municipality", key = "#municipalityId", unless = "#result == null || #result.isEmpty()")
    public Optional<Municipality> findById(String municipalityId) {
        return jpaRepository.findById(municipalityId).map(mapper::toDomain);
    }

    @Override
    public Page<Municipality> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user) {
        Specification<MunicipalityEntity> authSpec = (root, query, cb) -> {
            // if (!user.hasRole(CaseFolderGuard.ROLE_CASE_FOLDER_MANAGEMENT)) {
            //     return cb.equal(root.get("owner"), user.username());
            // }
            return cb.conjunction();
        };
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(authSpec, pageable);
            return page.map(mapper::toDomain);
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<MunicipalityEntity> spec = rootNode.accept(new CustomRsqlVisitor<MunicipalityEntity>());
            Specification<MunicipalityEntity> finalSpec = (spec == null) ? authSpec : spec.and(authSpec);
            var page = jpaRepository.findAll(finalSpec, pageable);
            return page.map(mapper::toDomain);
        }
        catch (Exception ex) {
            throw new BadRequestException("rsql.msg.err.parse", ex, rsql);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "municipality", key = "#result.id")
    public Municipality save(Municipality municipality) {
        if (municipality.id() != null && jpaRepository.existsById(municipality.id())) {
            throw new BadRequestException("municipality.msg.err.already-exists", municipality.id());
        }
        var entity = mapper.toEntity(municipality);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    @CachePut(value = "municipality", key = "#municipalityId")
    public Municipality update(String municipalityId, Municipality updatedData) {
        MunicipalityEntity current = jpaRepository.findById(municipalityId)
            .orElseThrow(() -> new BadRequestException("Province not found with id " + municipalityId));
        ProvinceEntity province = new ProvinceEntity();
        province.setId(updatedData.provinceId());
        current.setName(updatedData.name());
        current.setProvince(province);
        current.setUpdatedAt(updatedData.updatedAt() != null ? updatedData.updatedAt() : LocalDateTime.now());
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "municipality", key = "#municipalityId")
    public void deleteById(String municipalityId) {
        jpaRepository.deleteById(municipalityId);
    }

}
