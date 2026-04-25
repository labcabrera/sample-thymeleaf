package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetPostalCodeByIdQuery;
import org.labcabrera.sample.api.geo.application.ports.PostalCodeRepository;
import org.labcabrera.sample.api.geo.domain.PostalCode;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.labcabrera.sample.api.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPostalCodeByIdHandler implements QueryHandler<GetPostalCodeByIdQuery, PostalCode> {

    private final PostalCodeRepository postalCodeRepository;
    private final SecurityPort securityPort;

    @Override
    public PostalCode handle(GetPostalCodeByIdQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Get postal code by id '{}' (user: {})", query.postalCodeId(), user.username());
        return postalCodeRepository.findById(query.postalCodeId())
            .orElseThrow(() -> new NotFoundException("postalcode.msg.not-found", query.postalCodeId(), PostalCode.class));
    }

}
