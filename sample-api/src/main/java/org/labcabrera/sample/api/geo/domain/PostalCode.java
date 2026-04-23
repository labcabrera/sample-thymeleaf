package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostalCode {
    private String id;
    private String code;
    private String municipalityId;
    private String provinceId;
    private LocalDateTime createdAt;
}
