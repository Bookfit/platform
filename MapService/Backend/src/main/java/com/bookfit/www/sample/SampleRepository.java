package com.bookfit.www.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query(value = """
        SELECT s.*, ST_Distance(
            s.location,
            ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
        ) AS distance
        FROM sample s
        ORDER BY distance ASC
        LIMIT 10
        """, nativeQuery = true)
    List<Sample> findNearestSamples(@Param("lng") double lng, @Param("lat") double lat);

    @Query(value = """
    SELECT 
        s.id,
        s.name,
        ST_Y(s.location) as latitude,
        ST_X(s.location) as longitude,
        ST_Distance(
            s.location,
            ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
        ) as distance
    FROM sample s
    ORDER BY s.location <-> ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
    LIMIT 1
    """, nativeQuery = true)
    Object findNearestRaw(@Param("lng") double lng, @Param("lat") double lat);
}
