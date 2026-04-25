package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetMunicipalityByIdQuery;
import org.labcabrera.sample.api.geo.application.ports.MunicipalityRepository;
import org.labcabrera.sample.api.geo.domain.Municipality;
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
public class GetMunicipalityByIdHandler implements QueryHandler<GetMunicipalityByIdQuery, Municipality> {

    private final MunicipalityRepository municipalityRepository;
    private final SecurityPort securityPort;
    private final Guard<Municipality> municipalityGuard;

    public Municipality handle(GetMunicipalityByIdQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Getting municipality {} (user: {})", query.municipalityId(), user.username());
        var municipality = municipalityRepository
            .findById(query.municipalityId())
            .orElseThrow(() -> new NotFoundException("municipality.msg.not-found", query.municipalityId(), Municipality.class));
        municipalityGuard.checkRead(municipality, user);
        return municipality;
    }

}