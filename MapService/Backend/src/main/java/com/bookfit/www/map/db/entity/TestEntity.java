package com.bookfit.www.map.db.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@Schema(description = "샘플 엔티티 - 이름과 위치(좌표)를 저장합니다.")
public class TestEntity {

    @Id
    @GeneratedValue
    @Schema(description = "샘플 고유 ID", example = "1")
    private Long id;

    @Schema(description = "샘플 이름", example = "서울역")
    private String name;

    @Column(columnDefinition = "geometry(Point, 4326)")
    @Schema(description = "좌표 정보 (Point)", example = "POINT(126.9706 37.5547)")
    private Point location;
}
