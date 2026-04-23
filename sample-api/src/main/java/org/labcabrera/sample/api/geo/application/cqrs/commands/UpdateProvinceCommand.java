package org.labcabrera.sample.api.geo.application.cqrs.commands;

public record UpdateProvinceCommand(
    String provinceId,
    String code,
    String name,
    String countryCode
) {
}
