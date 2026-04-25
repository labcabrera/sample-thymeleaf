package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {
    private String id;
    private String name;
    private String countryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
