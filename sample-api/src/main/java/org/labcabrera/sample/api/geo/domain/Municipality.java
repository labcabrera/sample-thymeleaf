package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

public record Municipality(
    String id,
    String name,
    String provinceId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

}
