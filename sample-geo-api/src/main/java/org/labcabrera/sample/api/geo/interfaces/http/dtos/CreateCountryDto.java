package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateCountryDto(
    @NotNull String id,
    @NotNull String name) {
}
