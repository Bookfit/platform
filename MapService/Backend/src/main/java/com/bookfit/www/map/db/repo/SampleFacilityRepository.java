package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.SampleFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SampleFacilityRepository extends JpaRepository<SampleFacility, Integer> {
}
