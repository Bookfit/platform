package com.bookfit.www.sample;

import com.bookfit.www.sample.dto.SampleCreateRequest;
import com.bookfit.www.sample.dto.SampleWithDistanceResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(); // 공간 객체 생성용


    public List<Sample> findNearestWithDistance(double lng, double lat, double range) {
        return sampleRepository.findNearestSamplesOptimized2(lng, lat, range);
    }

    public List<Sample> findNearest(double lng, double lat) {
        return sampleRepository.findNearestSamples(lng, lat);
    }

    public Sample saveSample(SampleCreateRequest request) {
        Point point = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));
        point.setSRID(4326); // 꼭 SRID 설정

        Sample sample = new Sample();
        sample.setName(request.name());
        sample.setLocation(point);

        return sampleRepository.save(sample);
    }


    public SampleWithDistanceResponse findNearestWithDistance(double lng, double lat) {
        Object[] row = (Object[]) sampleRepository.findNearestRaw(lng, lat);
        return new SampleWithDistanceResponse(
                ((Number) row[0]).longValue(),     // id
                (String) row[1],                    // name
                ((Number) row[2]).doubleValue(),    // lat
                ((Number) row[3]).doubleValue(),    // lng
                ((Number) row[4]).doubleValue()     // distance (meter)
        );
    }
}
