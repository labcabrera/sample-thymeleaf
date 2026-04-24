package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProvinceDto", description = "Province information")
public record ProvinceDto(

	@Schema(name = "id", example = "ES-MAD", description = "Unique identifier of the province", requiredMode = Schema.RequiredMode.REQUIRED) String id,

	@Schema(name = "name", example = "MADRID", description = "Province name", requiredMode = Schema.RequiredMode.REQUIRED) String name,

	@Schema(name = "countryId", example = "ES", description = "Country identifier", requiredMode = Schema.RequiredMode.REQUIRED) String countryId,

	@Schema(name = "createdAt", description = "Creation timestamp") LocalDateTime createdAt,

	@Schema(name = "updatedAt", description = "Last update timestamp") LocalDateTime updatedAt) {
}
