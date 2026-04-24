package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers.ProvinceEntityMapper;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.labcabrera.sample.api.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.api.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
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
public class ProvinceRepositoryJpaAdapter implements ProvinceRepository {

    private final ProvinceJpaRepository jpaRepository;
    private final ProvinceEntityMapper mapper;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<Province> findByName(String name) {
        if (name != null && !name.isBlank()) {
            var e = jpaRepository.findByNameIgnoreCase(name);
            if (e.isPresent()) {
                return e.map(mapper::toDomain);
            }
        }
        return java.util.Optional.empty();
    }

    @Override
    @Cacheable(value = "province", key = "#provinceId", unless = "#result == null || #result.isEmpty()")
    public Optional<Province> findById(String provinceId) {
        return jpaRepository.findById(provinceId).map(mapper::toDomain);
    }

    @Override
    public Page<Province> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user) {
        Specification<ProvinceEntity> authSpec = (root, query, cb) -> {
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
            Specification<ProvinceEntity> spec = rootNode.accept(new CustomRsqlVisitor<ProvinceEntity>());
            Specification<ProvinceEntity> finalSpec = (spec == null) ? authSpec : spec.and(authSpec);
            var page = jpaRepository.findAll(finalSpec, pageable);
            return page.map(mapper::toDomain);
        }
        catch (Exception ex) {
            throw new BadRequestException("rsql.msg.err.parse", ex, rsql);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "province", key = "#result.id")
    public Province save(Province province) {
        if (province.id() != null && jpaRepository.existsById(province.id())) {
            throw new BadRequestException("province.msg.err.already-exists", province.id());
        }
        var entity = mapper.toEntity(province);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    @CachePut(value = "province", key = "#provinceId")
    public Province update(String provinceId, Province province) {
        var current = jpaRepository.findById(provinceId)
            .orElseThrow(() -> new BadRequestException(String.format("Province not found with id %s", provinceId)));
        current.setName(province.name());
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "province", key = "#provinceId")
    public void deleteById(String provinceId) {
        jpaRepository.deleteById(provinceId);
    }

}
