package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Address {
    private String id;
    private String streetName;
    private String streetNumber;
    private String additionalInfo;
    private String postalCode;
    private String municipalityName;
    private String provinceName;
    private String countryCode;
    private String municipalityId;
    private String provinceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}