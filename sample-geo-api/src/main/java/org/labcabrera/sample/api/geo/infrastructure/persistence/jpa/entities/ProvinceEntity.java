package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "province")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "code", length = 36)
    private String code;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "country_code", length = 10)
    private String countryCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

}
