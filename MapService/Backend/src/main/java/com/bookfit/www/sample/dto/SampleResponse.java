//package com.bookfit.www.sample.dto;
//
//import com.bookfit.www.sample.db.entity.SampleEntity;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//@Data
//@Schema(description = "좌표 정보 응답 DTO")
//public class SampleResponse {
//
//    @Schema(description = "샘플 ID", example = "1")
//    private Long id;
//
//    @Schema(description = "이름", example = "서울역")
//    private String name;
//
//    @Schema(description = "위도", example = "37.5547")
//    private double latitude;
//
//    @Schema(description = "경도", example = "126.9706")
//    private double longitude;
//
//    public SampleResponse(SampleEntity sampleEntity) {
//        this.id = sampleEntity.getId();
//        this.name = sampleEntity.getName();
//        this.latitude = sampleEntity.getLocation().getY(); // 위도
//        this.longitude = sampleEntity.getLocation().getX(); // 경도
//    }
//}
