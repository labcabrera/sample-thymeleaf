package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProvinceDto", description = "Province information")
public class ProvinceDto {

	@Schema(name = "id", example = "550e8400-e29b-41d4-a716-446655440000", description = "Unique identifier of the province", requiredMode = Schema.RequiredMode.REQUIRED)
	private String id;

	@Schema(name = "code", example = "MD", description = "Province code", requiredMode = Schema.RequiredMode.REQUIRED)
	private String code;

	@Schema(name = "name", example = "Madrid", description = "Province name", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@Schema(name = "countryCode", example = "ES", description = "ISO country code", requiredMode = Schema.RequiredMode.REQUIRED)
	private String countryCode;

	@Schema(name = "createdAt", description = "Creation timestamp")
	private LocalDateTime createdAt;

	@Schema(name = "updatedAt", description = "Last update timestamp")
	private LocalDateTime updatedAt;

}
