package com.bookfit.www.map.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SampleFacilityMapId implements java.io.Serializable {
    private static final long serialVersionUID = -9136909391786893236L;
    @NotNull
    @Column(name = "sample_id", nullable = false)
    private Long sampleId;

    @NotNull
    @Column(name = "sample_facility_id", nullable = false)
    private Integer sampleFacilityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SampleFacilityMapId entity = (SampleFacilityMapId) o;
        return Objects.equals(this.sampleId, entity.sampleId) &&
                Objects.equals(this.sampleFacilityId, entity.sampleFacilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sampleId, sampleFacilityId);
    }

}