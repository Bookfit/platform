package com.bookfit.www.map.dto.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapListStatusSummaryVO {
    private int totalCount;
    private int pendingCount;
    private List<CategoryVO> categories;
}