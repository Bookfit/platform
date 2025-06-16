package com.bookfit.www.map.dto.details;

import com.bookfit.www.map.dto.main.MapCategoryVO;
import com.bookfit.www.map.dto.search.CategoryVO;
import com.bookfit.www.map.dto.search.FacilitiesVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PostDetailsRequestDTO {

    @Schema(description = "사용자 ID", example = "737833749")
    private String userId;

    @Schema(description = "로그인 타입", example = "kakao")
    private String loginType;

    @Schema(description = "장소명", example = "역 1분거리 스터디 룸 카페")
    private String name;

    @Schema(description = "카테고리 목록", example = "[{\"code\":\"STUDYROOM\",\"name\":\"스터디룸\"}]")
    private List<MapCategoryVO> categories;

    @Schema(description = "주소", example = "서울 동작구 동작대로 129")
    private String address;

    @Schema(description = "상세 주소", example = "지하1층(사당동, 지하1층) 쌤스터디카페")
    private String detailAddress;

    @Schema(description = "위도", example = "37.488306")
    private Double lat;

    @Schema(description = "경도", example = "126.981925")
    private Double lon;

    @Schema(description = "평일 운영 시간", example = "09:00 - 21:00")
    private String weekdayHours;

    @Schema(description = "주말 운영 시간", example = "10:00 - 18:00")
    private String weekendHours;

    @Schema(description = "시설 목록", example = "[{\"code\":\"PARKING\",\"name\":\"주차\"}]")
    private List<FacilitiesCategoryVO> facilities;

    @Schema(description = "설명", example = "책과 커피를 함께 즐길 수 있는 조용한 공간입니다.")
    private String description;
}
