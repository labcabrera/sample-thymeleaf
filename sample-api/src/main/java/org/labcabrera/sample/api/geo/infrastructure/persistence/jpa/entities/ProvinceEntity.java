package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.labcabrera.sample.api.geo.domain.Province;

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

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", length = 200)
    private String name;

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

    public boolean merge(Province updated) {
        boolean modified = false;
        if (updated.code() != null && !updated.code().equals(this.code)) {
            this.code = updated.code();
            modified = true;
        }
        if (updated.name() != null && !updated.name().equals(this.name)) {
            this.name = updated.name();
            modified = true;
        }
        if (updated.countryCode() != null && !updated.countryCode().equals(this.countryCode)) {
            this.countryCode = updated.countryCode();
            modified = true;
        }
        return modified;
    }

}
