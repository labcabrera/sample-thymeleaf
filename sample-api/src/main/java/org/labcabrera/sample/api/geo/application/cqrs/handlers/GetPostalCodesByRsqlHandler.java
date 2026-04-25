package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetPostalCodesByRsqlQuery;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPostalCodesByRsqlHandler implements QueryHandler<GetPostalCodesByRsqlQuery, Page<PostalCode>> {

    private final PostalCodeRepository postalCodeRepository;
    private final SecurityPort securityPort;

    public Page<PostalCode> handle(GetPostalCodesByRsqlQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Find postal codes by rsql '{}' (user: {})", query.rsql(), user.username());
        return postalCodeRepository.findByRsql(query.rsql(), query.pageable(), user);
    }

}
