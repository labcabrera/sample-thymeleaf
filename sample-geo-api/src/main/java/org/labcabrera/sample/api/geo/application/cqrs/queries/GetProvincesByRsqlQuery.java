package org.labcabrera.sample.api.geo.application.cqrs.queries;

import org.springframework.data.domain.Pageable;

public record GetProvincesByRsqlQuery(
    String rsql,
    Pageable pageable) {
}
