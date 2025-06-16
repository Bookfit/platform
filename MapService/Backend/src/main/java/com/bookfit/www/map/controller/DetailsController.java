package com.bookfit.www.map.controller;

import com.bookfit.www.map.dto.details.PostDetailsRequestDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleRequestDTO;
import com.bookfit.www.map.dto.search.GetSearchSampleResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/map/details")
@Tag(name = "/map/details", description = "상세정보 페이지")
@RequiredArgsConstructor
@Slf4j
public class DetailsController {

    @GetMapping("")
    public ResponseEntity<Void> getDetails() {

        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<PostDetailsRequestDTO> saveDetails(@ModelAttribute PostDetailsRequestDTO request) {

        return ResponseEntity.ok().build();
    }
}
