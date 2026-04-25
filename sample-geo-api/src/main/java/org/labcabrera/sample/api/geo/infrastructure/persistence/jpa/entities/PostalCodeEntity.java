package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "postal_code")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostalCodeEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "code", length = 20)
    private String code;

    @ManyToOne(cascade = { CascadeType.ALL })
    private ProvinceEntity province;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

}
