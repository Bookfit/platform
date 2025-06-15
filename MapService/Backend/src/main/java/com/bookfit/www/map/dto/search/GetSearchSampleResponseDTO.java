package com.bookfit.www.map.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "등록공간 조회페이지")
@Data
public class GetSearchSampleResponseDTO {
    @Schema(description = "등록공간 조회페이지")
    private Integer id;
    private Integer userId;
    private String loginType;
    private String name;
    private String address;
    private String detailAddress;
    private Double lat;
    private Double lon;
    private String weekdayHours;
    private String weekendHours;
    private String description;
    private String status;

    private List<CategoryDTO> categories;
    private List<FacilitiesDTO> facilities;
}
