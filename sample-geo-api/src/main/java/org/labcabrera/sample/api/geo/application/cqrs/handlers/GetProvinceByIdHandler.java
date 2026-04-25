package org.labcabrera.sample.api.geo.application.cqrs.handlers;

import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvinceByIdQuery;
import org.labcabrera.sample.api.geo.application.ports.ProvinceRepository;
import org.labcabrera.sample.api.geo.domain.Province;
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
public class GetProvinceByIdHandler implements QueryHandler<GetProvinceByIdQuery, Province> {

    private final ProvinceRepository provinceRepository;
    private final SecurityPort securityPort;
    private final Guard<Province> provinceGuard;

    public Province handle(GetProvinceByIdQuery query) {
        var user = securityPort.requireCurrentUser();
        log.debug("Getting province {} (user: {})", query.provinceId(), user.username());
        var province = provinceRepository
            .findById(query.provinceId())
            .orElseThrow(() -> new NotFoundException("province.msg.not-found", query.provinceId(), Province.class));
        provinceGuard.checkRead(province, user);
        return province;
    }

}
