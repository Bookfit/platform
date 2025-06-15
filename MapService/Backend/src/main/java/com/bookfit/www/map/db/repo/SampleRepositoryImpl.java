package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.QSample;
import com.bookfit.www.map.db.entity.Sample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Page<Sample> findSamplesWithPaging(Pageable pageable) {
        QSample sample = QSample.sample;

        // 본문 조회
        List<Sample> content = queryFactory.selectFrom(sample)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory.selectFrom(sample)
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

}
