package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Municipality {
    private String id;
    private String code;
    private String name;
    private String provinceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
