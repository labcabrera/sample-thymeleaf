package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CountryDto", description = "Country information")
public class CountryDto {

    @Schema(name = "id", example = "550e8400-e29b-41d4-a716-446655440000", description = "Unique identifier of the country", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(name = "name", example = "Spain", description = "Country name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "createdAt", description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(name = "updatedAt", description = "Last update timestamp")
    private LocalDateTime updatedAt;

}
