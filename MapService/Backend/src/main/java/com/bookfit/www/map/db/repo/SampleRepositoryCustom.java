package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SampleRepositoryCustom {
    int updateStatusByIdAndUserId(Integer sampleId, Integer userId, String loginType, String status);
    Page<Sample> findSamplesWithPaging(Pageable pageable);
}
