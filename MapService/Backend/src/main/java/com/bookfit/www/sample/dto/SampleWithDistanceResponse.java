package com.bookfit.www.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "가장 가까운 장소와 거리 응답 정보")
public class SampleWithDistanceResponse {

    @Schema(description = "장소 ID", example = "1")
    private Long id;

    @Schema(description = "장소 이름", example = "서울역")
    private String name;

    @Schema(description = "위도", example = "37.5547")
    private double latitude;

    @Schema(description = "경도", example = "126.9706")
    private double longitude;

    @Schema(description = "입력 좌표로부터의 거리 (미터)", example = "123.45")
    private double distance;

    public SampleWithDistanceResponse(Long id, String name, double latitude, double longitude, double distance) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
