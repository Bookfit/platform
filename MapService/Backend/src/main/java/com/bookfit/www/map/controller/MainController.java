package com.bookfit.www.map.controller;

import com.bookfit.www.map.dto.main.MapListStatusSummaryVO;
import com.bookfit.www.map.dto.main.PostMainRequestDTO;
import com.bookfit.www.map.service.main.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map/main")
@Tag(name = "/map/main", description = "메인화면 페이지")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MapService mapService;

    @GetMapping
    @Operation(summary = "화면 구성 정보 요청", description = "화면 표현을 위한 정보를 반환 합니다.")
    public ResponseEntity<Object> getMain() {
        return mapService.getBffMain();
//        return "";
    }

    @PostMapping
    @Operation(summary = "간편 등록 수행", description = "메인화면에서 간편 등록을 요청 합니다.")
    public ResponseEntity<Object> saveMain(@RequestBody PostMainRequestDTO postMainRequestDTO) {
        return mapService.saveBffMain(postMainRequestDTO);
    }
}
