package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProvinceDto(
    @NotNull @NotBlank String id,
    @NotNull @NotBlank String name,
    @NotNull @NotBlank String countryId) {
}
