//package com.bookfit.www.sample.db.repo;
//
//import com.bookfit.www.sample.db.entity.SampleEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
//
//    @Query(value = """
//            SELECT s.*, ST_Distance(
//                s.location,
//                ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
//            ) AS distance
//            FROM sampleEntity s
//            ORDER BY distance ASC
//            LIMIT 10
//            """, nativeQuery = true)
//    List<SampleEntity> findNearestSamples(@Param("lng") double lng, @Param("lat") double lat);
//
//    @Query(value = """
//            SELECT
//                s.id,
//                s.name,
//                ST_Y(s.location) as latitude,
//                ST_X(s.location) as longitude,
//                ST_Distance(
//                    s.location,
//                    ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
//                ) as distance
//            FROM sampleEntity s
//            ORDER BY s.location <-> ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)
//            LIMIT 1
//            """, nativeQuery = true)
//    Object findNearestRaw(@Param("lng") double lng, @Param("lat") double lat);
//
//    // 성능 최적화를 위한 ST_DWithin 사용 버전 (더 빠름)
//    @Query(value = """
//              SELECT s.*, ST_Distance(
//                 s.location::geography,
//                 ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography
//             ) AS distance_meters
//             FROM sampleEntity s
//             WHERE ST_DWithin(
//                 s.location::geography,
//                 ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
//                 :radiusMeters
//             )
//             ORDER BY distance_meters ASC;
//            """, nativeQuery = true)
//    List<SampleEntity> findNearestSamplesOptimized2(@Param("lng") double lng, @Param("lat") double lat, @Param("radiusMeters") double radiusMeters);
//}
