package com.bookfit.www.sample.dto;

import com.bookfit.www.sample.Sample;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "좌표 정보 응답 DTO")
public class SampleResponse {

    @Schema(description = "샘플 ID", example = "1")
    private Long id;

    @Schema(description = "이름", example = "서울역")
    private String name;

    @Schema(description = "위도", example = "37.5547")
    private double latitude;

    @Schema(description = "경도", example = "126.9706")
    private double longitude;

    public SampleResponse(Sample sample) {
        this.id = sample.getId();
        this.name = sample.getName();
        this.latitude = sample.getLocation().getY(); // 위도
        this.longitude = sample.getLocation().getX(); // 경도
    }
}
