package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Un codigo postal puede estar asociado a varios municipios pero solo a una provincia.
 */
@Data
public class PostalCode {
    private String id;
    private String code;
    private String provinceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
