package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Category;
import com.bookfit.www.map.db.entity.SampleFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SampleFacilityRepository extends JpaRepository<SampleFacility, Integer> {
    List<SampleFacility> findByCodeIn(List<String> codes);
}
