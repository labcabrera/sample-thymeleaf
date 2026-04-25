package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateMunicipalityDto(
    @NotNull String name,
    @NotNull String provinceId) {
}
