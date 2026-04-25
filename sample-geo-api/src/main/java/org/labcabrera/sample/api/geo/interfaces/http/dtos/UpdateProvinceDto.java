package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public record UpdateProvinceDto(
    @NotNull String provinceId,
    Optional<String> name,
    Optional<String> countryId) {
}
