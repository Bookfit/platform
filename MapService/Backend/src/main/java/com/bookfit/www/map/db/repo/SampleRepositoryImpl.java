package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.QSample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class SampleRepositoryImpl implements SampleRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    @Transactional
    public int updateStatusByIdAndUserId(Integer sampleId, Integer userId, String loginType, String status) {
        QSample sample = QSample.sample;

        return (int) queryFactory
                .update(sample)
                .set(sample.status, status)
                .where(sample.sampleId.eq(sampleId)
                        .and(sample.user.socialType.eq(loginType))
                        .and(sample.user.userId.eq(userId)))
                .execute();
    }
}
