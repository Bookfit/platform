package com.bookfit.www.map.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "등록공간 저장요청승인")
@Data
public class  PatchSearchRequestDTO {

        @NotNull(message = "sampleId는 필수입니다.")
        @Schema(description = "장소 ID", example = "1")
        Integer sampleId;

        @NotNull(message = "userId는 필수입니다.")
        @Schema(description = "사용자 ID", example = "1")
        Integer userId;

        @NotBlank(message = "longitude는 필수입니다.")
        @Schema(description = "로그인 타입", example = "kakao")
        String longitude;

        @NotBlank(message = "status는 필수입니다.")
        @Schema(description = "등록/요청중", example = "REGISTERED")
        String status;
}