package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "street_name", length = 200)
    private String streetName;

    @Column(name = "street_number", length = 50)
    private String streetNumber;

    @Column(name = "additional_info", length = 400)
    private String additionalInfo;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private PostalCodeEntity postalCode;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private MunicipalityEntity municipality;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private ProvinceEntity province;

    @Column(name = "country_code", length = 10)
    private String countryCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

}
