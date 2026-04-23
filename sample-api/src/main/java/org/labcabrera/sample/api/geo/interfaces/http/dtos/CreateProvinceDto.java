package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateProvinceDto(
    @NotNull String code,
    @NotNull String name,
    @NotNull String countryCode
) {
}

