package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetCountriesByRsqlQuery;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCountriesByRsqlHandler implements QueryHandler<GetCountriesByRsqlQuery, Page<Country>> {

    private final CountryRepository countryRepository;
    private final SecurityPort securityPort;

    public Page<Country> handle(GetCountriesByRsqlQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Find countries by rsql '{}' (user: {})", query.rsql(), user.username());
        return countryRepository.findByRsql(query.rsql(), query.pageable(), user);
    }

}
