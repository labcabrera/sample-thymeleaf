package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MunicipalityRepository {

    Optional<Municipality> findById(String id);

    Page<Municipality> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    Optional<Municipality> findByCodeOrName(String code, String name);

    Municipality save(Municipality entity);

    Municipality update(String id, Municipality updatedData);

    void deleteById(String id);

}
