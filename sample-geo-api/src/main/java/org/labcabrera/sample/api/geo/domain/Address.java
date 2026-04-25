package org.labcabrera.sample.api.geo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Address {
    private String id;
    private String countryId;
    private String provinceId;
    private String municipalityId;
    private String postalCodeId;
    private String streetName;
    private String streetNumber;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}