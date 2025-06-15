package com.bookfit.www.map.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sample_facility")
public class SampleFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sample_facility_id_gen")
    @SequenceGenerator(name = "sample_facility_id_gen", sequenceName = "sample_facility_id_seq", allocationSize = 1)
    @Column(name = "sample_facility_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

}