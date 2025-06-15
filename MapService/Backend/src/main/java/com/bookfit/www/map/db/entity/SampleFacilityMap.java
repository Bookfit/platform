package com.bookfit.www.map.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "sample_facility_map")
public class SampleFacilityMap {
    @SequenceGenerator(name = "sample_facility_map_id_gen", sequenceName = "sample_facility_id_seq", allocationSize = 1)
    @EmbeddedId
    private SampleFacilityMapId id;

    @MapsId("sampleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sample_id", nullable = false)
    private Sample sample;

    @MapsId("sampleFacilityId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sample_facility_id", nullable = false)
    private SampleFacility sampleFacility;

}