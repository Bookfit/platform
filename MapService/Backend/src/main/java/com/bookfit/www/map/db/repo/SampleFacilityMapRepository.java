package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.SampleFacilityMap;
import com.bookfit.www.map.db.entity.SampleFacilityMapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleFacilityMapRepository extends JpaRepository<SampleFacilityMap, SampleFacilityMapId> {
}
