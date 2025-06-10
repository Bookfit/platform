package com.bookfit.www.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "좌표 등록 요청")
public record SampleCreateRequest(
        @Schema(description = "장소 이름", example = "서울역")
        String name,

        @Schema(description = "위도", example = "37.5547")
        double latitude,

        @Schema(description = "경도", example = "126.9706")
        double longitude
) {}
