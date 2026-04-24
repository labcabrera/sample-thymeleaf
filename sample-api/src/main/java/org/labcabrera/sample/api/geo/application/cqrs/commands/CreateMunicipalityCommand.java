package org.labcabrera.sample.api.geo.application.cqrs.commands;

import jakarta.validation.constraints.NotNull;

/**
 * Command to create a Municipality.
 */
public record CreateMunicipalityCommand(
	@NotNull String name,
	@NotNull String provinceId) {
}
