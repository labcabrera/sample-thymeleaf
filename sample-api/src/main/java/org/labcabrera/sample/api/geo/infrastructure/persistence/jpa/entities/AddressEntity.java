package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "country_id")
    private PostalCodeEntity country;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "municipality_id")
    private MunicipalityEntity municipality;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "province_id")
    private ProvinceEntity province;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "postal_code_id")
    private PostalCodeEntity postalCode;

    @Column(name = "street_name", length = 200)
    private String streetName;

    @Column(name = "street_number", length = 50)
    private String streetNumber;

    @Column(name = "additional_info", length = 400)
    private String additionalInfo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

}
