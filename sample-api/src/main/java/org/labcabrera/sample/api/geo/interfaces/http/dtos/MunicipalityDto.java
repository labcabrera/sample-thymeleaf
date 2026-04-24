package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MunicipalityDto", description = "Municipality information")
public record MunicipalityDto(

    @Schema(name = "id", example = "550e8400-e29b-41d4-a716-446655440000", description = "Unique identifier of the municipality", requiredMode = Schema.RequiredMode.REQUIRED) String id,

    @Schema(name = "name", example = "Madrid Center", description = "Municipality name", requiredMode = Schema.RequiredMode.REQUIRED) String name,

    @Schema(name = "provinceId", example = "550e8400-e29b-41d4-a716-446655440001", description = "Province id this municipality belongs to", requiredMode = Schema.RequiredMode.REQUIRED) String provinceId,

    @Schema(name = "createdAt", description = "Creation timestamp") LocalDateTime createdAt,

    @Schema(name = "updatedAt", description = "Last update timestamp") LocalDateTime updatedAt) {
}
