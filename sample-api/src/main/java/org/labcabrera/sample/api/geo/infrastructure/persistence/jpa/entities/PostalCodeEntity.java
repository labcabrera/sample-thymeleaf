package org.labcabrera.sample.api.geo.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.labcabrera.sample.api.geo.domain.PostalCode;

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

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private MunicipalityEntity municipality;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    public boolean merge(PostalCode updated) {
        boolean modified = false;
        if (updated.getCode() != null && !updated.getCode().equals(this.code)) {
            this.code = updated.getCode();
            modified = true;
        }
        return modified;
    }

}
