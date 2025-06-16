package com.bookfit.www.map.service.details;

import com.bookfit.www.map.db.entity.Sample;
import com.bookfit.www.map.db.entity.User;
import com.bookfit.www.map.db.repo.CategoryRepository;
import com.bookfit.www.map.db.repo.SampleFacilityRepository;
import com.bookfit.www.map.db.repo.SampleRepository;
import com.bookfit.www.map.db.repo.UserRepository;
import com.bookfit.www.map.dto.details.GetDetailsResponseDTO;
import com.bookfit.www.map.dto.details.MapFacilitiesVO;
import com.bookfit.www.map.dto.details.PostDetailsRequestDTO;
import com.bookfit.www.map.dto.main.MapCategoryVO;
import com.bookfit.www.map.dto.search.CategoryVO;
import com.bookfit.www.map.dto.search.FacilitiesVO;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailsService {

    private final CategoryRepository categoryRepository;
    private final SampleFacilityRepository facilityRepository;
    private final SampleRepository sampleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public GetDetailsResponseDTO getAllCategoriesAndFacilities() {
        GetDetailsResponseDTO dto = new GetDetailsResponseDTO();

        dto.setCategories(
                categoryRepository.findAll().stream()
                        .map(c -> new CategoryVO(c.getCode(), c.getName()))
                        .collect(Collectors.toList())
        );

        dto.setFacilities(
                facilityRepository.findAll().stream()
                        .map(f -> new FacilitiesVO(f.getCode(), f.getName()))
                        .collect(Collectors.toList())
        );

        return dto;
    }


    public ResponseEntity<Object> saveDetails(PostDetailsRequestDTO request) {

        try {
            Double lat = request.getLat();
            Double lon = request.getLon();

            if (lat == null || lon == null) {
                throw new IllegalArgumentException("위도 또는 경도 값이 비어있습니다.");
            }
            if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
                throw new IllegalArgumentException("위도/경도 값이 유효 범위를 벗어났습니다.");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("위도, 경도 위치 정보가 잘못되었습니다.");
        }

        GeometryFactory geometryFactory = new GeometryFactory(); // 공간 객체 생성용
        List<String> selectCategory = request.getCategories().stream().map(MapCategoryVO::getCode).collect(Collectors.toList());
        List<String> selectFacilities = request.getFacilities().stream().map(MapFacilitiesVO::getCode).collect(Collectors.toList());

        Point point = geometryFactory.createPoint(new Coordinate(request.getLon(), request.getLat()));
        point.setSRID(4326); // 꼭 SRID 설정

        try {
            User user = userRepository.findUserBySocialUniqueIdAndSocialType(
                    request.getUserId().toString(),
                    request.getLoginType()
            ).orElseThrow(() ->
                    new RuntimeException("User not found")
            );
            sampleRepository.save(Sample.builder()
                    .user(user)
                    .name(request.getName())
                    .status("PENDING")
                    .description(request.getDescription())
                    .weekdayHours(request.getWeekdayHours())
                    .weekendHours(request.getWeekendHours())
                    .address(request.getAddress())
                    .detailAddress(request.getDetailAddress())
                    .location(point)
                    .categories(categoryRepository.findByCodeIn(selectCategory))
                    .facilities(facilityRepository.findByCodeIn(selectFacilities))
                    .build());
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            } else {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(e.getMessage());
            }
        }

        return ResponseEntity.ok("");
    }
}

