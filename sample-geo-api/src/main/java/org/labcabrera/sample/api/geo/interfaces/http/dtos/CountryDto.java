package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CountryDto", description = "Country information")
public record CountryDto(

    @Schema(name = "id", example = "ES", description = "Unique identifier of the country", requiredMode = Schema.RequiredMode.REQUIRED) String id,

    @Schema(name = "name", example = "SPAIN", description = "Country name", requiredMode = Schema.RequiredMode.REQUIRED) String name,

    @Schema(name = "createdAt", description = "Creation timestamp") LocalDateTime createdAt,

    @Schema(name = "updatedAt", description = "Last update timestamp") LocalDateTime updatedAt) {
}
