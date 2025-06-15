package com.bookfit.www.map.db.repo;

import com.bookfit.www.map.db.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SampleRepository extends JpaRepository<Sample, Integer>, SampleRepositoryCustom  {

public interface SampleRepository extends JpaRepository<Sample, Integer> {

    @Query(value = "SELECT status AS status, COUNT(*)::INT AS count " +
            "FROM sample WHERE status IN ('PENDING', 'REGISTERED') GROUP BY status",
            nativeQuery = true)
    public List<MapListStatusCountVO> getStatusCount();

}
