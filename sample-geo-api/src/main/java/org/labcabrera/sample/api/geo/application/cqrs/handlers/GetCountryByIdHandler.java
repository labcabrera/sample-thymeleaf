package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetCountryByIdQuery;
import org.labcabrera.sample.api.geo.application.ports.CountryRepository;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.shared.application.Guard;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCountryByIdHandler implements QueryHandler<GetCountryByIdQuery, Country> {

    private final CountryRepository countryRepository;
    private final SecurityPort securityPort;
    private final Guard<Country> countryGuard;

    public Country handle(GetCountryByIdQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Getting country {} (user: {})", query.countryId(), user.username());
        var country = countryRepository
            .findById(query.countryId())
            .orElseThrow(() -> new NotFoundException("country.msg.not-found", query.countryId(), Country.class));
        countryGuard.checkRead(country, user);
        return country;
    }

}
