package com.bookfit.www.map.controller;

import com.bookfit.www.map.dto.details.GetDetailsResponseDTO;
import com.bookfit.www.map.dto.details.PostDetailsRequestDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleRequestDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleResponseDTO;
import com.bookfit.www.map.service.details.DetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map/details")
@Tag(name = "/map/details", description = "상세정보 페이지")
@RequiredArgsConstructor
@Slf4j
public class DetailsController {

    private final DetailsService detailsService;

    @GetMapping("")
    public ResponseEntity<GetDetailsResponseDTO> getDetails() {

        return ResponseEntity.ok(detailsService.getAllCategoriesAndFacilities());
    }

    @PostMapping()
    public ResponseEntity<PostDetailsRequestDTO> saveDetails(@RequestBody PostDetailsRequestDTO request) {

        return ResponseEntity.ok(request);
    }

    @GetMapping("/meta")
    @Operation(summary = "화면 구성 정보 요청", description = "화면 표현을 위한 정보를 반환 합니다.")
    public ResponseEntity<Map<String, Object>> getMockMetaData() {
        List<Map<String, String>> categories = List.of(
                Map.of("code", "BOOKSTORE", "name", "서점"),
                Map.of("code", "STUDY_ROOM", "name", "스터디룸"),
                Map.of("code", "CAFE", "name", "카페"),
                Map.of("code", "THEATER", "name", "공연장")
        );

        List<Map<String, String>> facilities = List.of(
                Map.of("code", "PARKING", "name", "주차"),
                Map.of("code", "DRINKS", "name", "음료"),
                Map.of("code", "POWER_OUTLET", "name", "콘센트"),
                Map.of("code", "MONITOR", "name", "모니터")
        );

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("facilities", facilities);

        return ResponseEntity.ok(response);
    }
}
