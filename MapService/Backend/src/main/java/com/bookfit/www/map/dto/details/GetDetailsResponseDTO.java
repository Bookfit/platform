package com.bookfit.www.map.dto.details;

import com.bookfit.www.map.dto.search.CategoryVO;
import com.bookfit.www.map.dto.search.FacilitiesVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "상세정보페이지")
public class GetDetailsResponseDTO {

    @Schema(description = "카테고리 목록", example = """
        [
          {"code": "BOOKSTORE", "name": "서점"},
          {"code": "STUDY_ROOM", "name": "스터디룸"},
          {"code": "CAFE", "name": "카페"},
          {"code": "THEATER", "name": "공연장"}
        ]
    """)
    private List<CategoryVO> categories;

    @Schema(description = "시설 목록", example = """
        [
          {"code": "PARKING", "name": "주차"},
          {"code": "DRINKS", "name": "음료"},
          {"code": "POWER_OUTLET", "name": "콘센트"},
          {"code": "MONITOR", "name": "모니터"}
        ]
    """)
    private List<FacilitiesVO> facilities;
}
