package com.bookfit.www.map.controller;

import com.bookfit.www.map.dto.search.PatchSearchRequestDTO;
import com.bookfit.www.map.service.search.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/search")
@Tag(name = "SearchController", description = "등록공간")
public class SearchController {
    private final SearchService searchService;

    @PatchMapping("")
    public ResponseEntity<Void> updateStatus(@RequestBody PatchSearchRequestDTO request) {
        searchService.updateStatus(request);
        return ResponseEntity.ok().build(); // HTTP 200 OK
    }
}
