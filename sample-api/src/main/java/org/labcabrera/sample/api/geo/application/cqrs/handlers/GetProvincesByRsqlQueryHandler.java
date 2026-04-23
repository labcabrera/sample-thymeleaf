package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvincesByRsqlQuery;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.shared.application.QueryHandler;
import org.labcabrera.sample.api.shared.application.SecurityPort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProvincesByRsqlQueryHandler implements QueryHandler<GetProvincesByRsqlQuery, Page<Province>> {

    private final ProvinceRepository provinceRepository;
    private final SecurityPort securityPort;

    public Page<Province> handle(GetProvincesByRsqlQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Find provinces by rsql '{}' (user: {})", query.rsql(), user.username());
        return provinceRepository.findByRsql(query.rsql(), query.pageable(), user);
    }

}
