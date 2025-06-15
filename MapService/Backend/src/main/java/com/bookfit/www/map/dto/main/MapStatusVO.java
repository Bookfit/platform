package com.bookfit.www.map.dto.main;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapStatusVO {
    private String code;  /*PENDING, REGISTERED*/
    private String name;
}
