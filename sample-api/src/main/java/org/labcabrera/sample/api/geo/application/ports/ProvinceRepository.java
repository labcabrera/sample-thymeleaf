package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProvinceRepository {

    Optional<Province> findById(String id);

    Page<Province> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    Province save(Province entity);

    Province update(String id, Province updatedData);

    void deleteById(String id);

}
