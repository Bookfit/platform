package com.bookfit.www.map.controller;

import com.bookfit.www.map.dto.search.GetSearchSampleRequestDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleResponseDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleResquestDTO;
import com.bookfit.www.map.dto.search.PatchSearchRequestDTO;
import com.bookfit.www.map.service.search.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/search")
@Tag(name = "SearchController", description = "등록공간")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("")
    public ResponseEntity<List<GetSearchSampleResponseDTO>> search(@ModelAttribute GetSearchSampleRequestDTO request) {
        Sort sort = Sort.unsorted();
        if (request.getSort() != null && request.getSort().contains(",")) {
            String[] parts = request.getSort().split(",");
            sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        List<GetSearchSampleResponseDTO> result = searchService.searchSamples(pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("")
    public ResponseEntity<Void> updateStatus(@RequestBody PatchSearchRequestDTO request) {
        searchService.updateStatus(request);
        return ResponseEntity.ok().build(); // HTTP 200 OK
    }
}
