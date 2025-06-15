package com.bookfit.www.map.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSearchSampleRequestDTO {

    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;

    @Schema(description = "페이지당 항목 수", example = "10")
    private int size = 10;

    @Schema(description = "정렬 기준 (예: name,asc 또는 name,desc)", example = "name,asc")
    private String sort;
}
