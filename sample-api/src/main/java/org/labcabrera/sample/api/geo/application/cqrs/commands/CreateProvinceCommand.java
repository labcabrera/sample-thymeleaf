package org.labcabrera.sample.api.geo.application.cqrs.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProvinceCommand(
    @NotNull @NotBlank String id,

    @NotNull @NotBlank String name,

    @NotNull @NotBlank String countryId) {
}
