package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

public record Country(
    String id,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
