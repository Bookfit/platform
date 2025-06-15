package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Integer> {
}
