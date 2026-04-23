package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.application.services.CaseFolderGuard;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers.ProvinceEntityMapper;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.labcabrera.sample.api.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.api.shared.domain.exceptions.NotModifiedException;
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
public class ProvinceRepositoryJpaAdapter implements ProvinceRepository {

    private final ProvinceJpaRepository jpaRepository;
    private final ProvinceEntityMapper mapper;
    private final RSQLParser rsqlParser;

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
        try {
            if (province.getId() != null && jpaRepository.existsById(province.getId())) {
                throw new BadRequestException("province.msg.err.already-exists", province.getId());
            }
            var entity = mapper.toEntity(province);
            var savedEntity = jpaRepository.save(entity);
            return mapper.toDomain(savedEntity);
        }
        catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("case-folder.msg.err.data-integrity", ex);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "province", key = "#provinceId")
    public Province update(String provinceId, Province caseFolder) {
        var current = jpaRepository.findById(provinceId)
            .orElseThrow(() -> new BadRequestException("Province not found with id " + provinceId));
        boolean modified = current.merge(caseFolder);
        if (!modified) {
            throw new NotModifiedException("province.msg.err.not-modified", provinceId);
        }
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
