package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.SampleCategoryMap;
import com.bookfit.www.map.db.entity.SampleCategoryMapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleCategoryMapRepository extends JpaRepository<SampleCategoryMap, SampleCategoryMapId> {
}
