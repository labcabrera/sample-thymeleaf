package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public record UpdateMunicipalityDto(
    @NotNull String municipalityId,
    Optional<String> name,
    Optional<String> provinceId) {
}
