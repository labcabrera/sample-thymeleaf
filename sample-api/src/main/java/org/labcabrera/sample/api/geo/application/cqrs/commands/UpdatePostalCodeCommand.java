package org.labcabrera.sample.api.geo.application.cqrs.commands;

import jakarta.validation.constraints.NotNull;

public record UpdatePostalCodeCommand(
    @NotNull String postalCodeId,
    @NotNull String code,
    String provinceId) {
}
