package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostalCodeRepository {

    Optional<PostalCode> findById(String id);

    Page<PostalCode> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    Optional<PostalCode> findByCode(String code);

    PostalCode save(PostalCode entity);

    PostalCode update(String id, PostalCode updatedData);

    void deleteById(String id);

}
