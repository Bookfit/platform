package com.bookfit.www.map.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "등록공간 저장요청승인")
@Data
public class  PatchSearchRequestDTO {
        @Schema(description = "장소 ID", example = "1")
        Integer sampleId;

        @Schema(description = "사용자 ID", example = "1")
        Integer userId;

        @Schema(description = "로그인 타입", example = "kakao")
        String longitude;

        @Schema(description = "등록/요청중", example = "REGISTERED")
        String status;
        }