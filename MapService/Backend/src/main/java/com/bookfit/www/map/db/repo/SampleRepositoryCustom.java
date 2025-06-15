package com.bookfit.www.map.db.repo;

public interface SampleRepositoryCustom {
    int updateStatusByIdAndUserId(Integer sampleId, Integer userId, String loginType, String status);
}
