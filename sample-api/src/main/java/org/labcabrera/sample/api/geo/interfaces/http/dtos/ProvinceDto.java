package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {

	private String id;
	private String code;
	private String name;
	private String countryCode;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
