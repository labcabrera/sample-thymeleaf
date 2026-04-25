package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.PostalCodeEntity;
import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.mappers.PostalCodeEntityMapper;
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
public class PostalCodeRepositoryJpaAdapter implements PostalCodeRepository {

    private final PostalCodeJpaRepository jpaRepository;
    private final PostalCodeEntityMapper mapper;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<PostalCode> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<PostalCode> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user) {
        Specification<PostalCodeEntity> authSpec = (root, query, cb) -> cb.conjunction();
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(authSpec, pageable);
            return page.map(mapper::toDomain);
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<PostalCodeEntity> spec = rootNode.accept(new CustomRsqlVisitor<PostalCodeEntity>());
            Specification<PostalCodeEntity> finalSpec = (spec == null) ? authSpec : spec.and(authSpec);
            var page = jpaRepository.findAll(finalSpec, pageable);
            return page.map(mapper::toDomain);
        }
        catch (Exception ex) {
            throw new BadRequestException("rsql.msg.err.parse", ex, rsql);
        }
    }

    @Override
    public Optional<PostalCode> findByCode(String code) {
        if (code != null && !code.isBlank()) {
            return jpaRepository.findByCodeIgnoreCase(code).map(mapper::toDomain);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public PostalCode save(PostalCode postalCode) {
        if (postalCode.getId() != null && jpaRepository.existsById(postalCode.getId())) {
            throw new BadRequestException("postalcode.msg.err.already-exists", postalCode.getId());
        }
        var entity = mapper.toEntity(postalCode);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public PostalCode update(String id, PostalCode updatedData) {
        PostalCodeEntity current = jpaRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Postal code not found with id " + id));
        current.setCode(updatedData.getCode());
        if (updatedData.getProvinceId() != null) {
            var province = new org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.ProvinceEntity();
            province.setId(updatedData.getProvinceId());
            current.setProvince(province);
        }
        current.setUpdatedAt(LocalDateTime.now());
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

}
