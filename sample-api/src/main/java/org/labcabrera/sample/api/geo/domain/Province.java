package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

public record Province(
    String id,
    String code,
    String name,
    String countryCode,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
