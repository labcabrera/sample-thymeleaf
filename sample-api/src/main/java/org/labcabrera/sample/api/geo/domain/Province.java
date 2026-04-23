package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

public record Province(
    String id,
    String name,
    String countryId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
