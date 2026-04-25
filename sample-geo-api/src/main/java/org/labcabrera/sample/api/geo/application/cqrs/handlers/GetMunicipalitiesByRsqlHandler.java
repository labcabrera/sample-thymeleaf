package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetMunicipalitiesByRsqlQuery;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetMunicipalitiesByRsqlHandler implements QueryHandler<GetMunicipalitiesByRsqlQuery, Page<Municipality>> {

    private final MunicipalityRepository municipalityRepository;
    private final SecurityPort securityPort;

    public Page<Municipality> handle(GetMunicipalitiesByRsqlQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Find municipalities by rsql '{}' (user: {})", query.rsql(), user.username());
        return municipalityRepository.findByRsql(query.rsql(), query.pageable(), user);
    }

}
