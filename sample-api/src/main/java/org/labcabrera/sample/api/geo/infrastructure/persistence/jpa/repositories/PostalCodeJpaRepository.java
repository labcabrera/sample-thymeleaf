package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities.PostalCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeJpaRepository extends
    JpaRepository<PostalCodeEntity, String>,
    JpaSpecificationExecutor<PostalCodeEntity> {

    Optional<PostalCodeEntity> findByCodeIgnoreCase(String code);

}
