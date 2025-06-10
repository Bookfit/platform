package com.bookfit.www.sample;

import com.bookfit.www.sample.dto.SampleCreateRequest;
import com.bookfit.www.sample.dto.SampleResponse;
import com.bookfit.www.sample.dto.SampleWithDistanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
@Tag(name = "Sample", description = "샘플 좌표 관리 API")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/near")
    @Operation(summary = "가까운 좌표 조회", description = "주어진 위도/경도에서 가까운 좌표를 반환합니다.")
    public List<SampleResponse> findNearestSamples(
            @Parameter(description = "경도 (Longitude)", example = "126.9780")
            @RequestParam double lng,

            @Parameter(description = "위도 (Latitude)", example = "37.5665")
            @RequestParam double lat
    ) {
        return sampleService.findNearest(lng, lat).stream()
                .map(SampleResponse::new)
                .toList();
    }

    @Operation(summary = "좌표 저장", description = "이름과 위도/경도를 입력받아 샘플 좌표를 저장합니다.")
    @PostMapping
    public Sample createSample(@RequestBody SampleCreateRequest request) {
        return sampleService.saveSample(request);
    }

    @GetMapping("/nearest-with-distance")
    @Operation(summary = "가장 가까운 위치와 거리 조회", description = "입력한 위도/경도에서 가장 가까운 위치와 거리를 반환합니다.")
    public SampleWithDistanceResponse findNearestWithDistance(
            @Parameter(description = "경도 (Longitude)", example = "126.9780")
            @RequestParam double lng,

            @Parameter(description = "위도 (Latitude)", example = "37.5665")
            @RequestParam double lat
    ) {
        return sampleService.findNearestWithDistance(lng, lat);
    }
}
