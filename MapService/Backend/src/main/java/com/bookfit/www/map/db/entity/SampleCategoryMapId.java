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
public class SampleCategoryMapId implements java.io.Serializable {
    private static final long serialVersionUID = 5753078026300479298L;
    @NotNull
    @Column(name = "sample_id", nullable = false)
    private Long sampleId;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SampleCategoryMapId entity = (SampleCategoryMapId) o;
        return Objects.equals(this.sampleId, entity.sampleId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sampleId, categoryId);
    }

}