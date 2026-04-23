package org.labcabrera.sample.api.geo.application.ports;

import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CountryRepository {

    Optional<Country> findById(String id);

    Page<Country> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    Optional<Country> findByName(String name);

    Country save(Country entity);

    Country update(String id, Country updatedData);

    void deleteById(String id);

}
