package com.bookfit.www.map.dto.main;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "간편 등록 저장 요청")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMainRequestDTO {
    @Schema(description = "사용자 고유 ID", example = "737833749")
    private Integer userId;
    @Schema(description = "소셜 로그인 종류", example = "kakao")
    private String loginType;
    @Schema(description = "장소 이름", example = "테스트장소1")
    private String name;
    @Schema(description = "장소 설명", example = "개발용 테스트 장소 설명 입력부")
    private String description;
    @Schema(description = "장소 카테고리", example = "")
    private List<MapCategoryVO> categories;
}
