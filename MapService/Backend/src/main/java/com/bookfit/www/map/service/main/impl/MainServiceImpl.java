package com.bookfit.www.map.service.main.impl;

import com.bookfit.www.map.db.repo.CategoryRepository;
import com.bookfit.www.map.db.repo.SampleRepository;
import com.bookfit.www.map.db.repo.UserRepository;
import com.bookfit.www.map.dto.main.MapListStatusCountVO;
import com.bookfit.www.map.dto.main.MapListStatusSummaryVO;
import com.bookfit.www.map.service.main.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainServiceImpl implements MapService {
    private final UserRepository userRepository;
    private final SampleRepository sampleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<MapListStatusCountVO> getStatusCount() {
        return sampleRepository.getStatusCount();
    }

    @Override
    public ResponseEntity<Object> getBffMain() {

        MapListStatusSummaryVO responseVO = MapListStatusSummaryVO.builder()
                .categories(categoryRepository.findAllMappedCategoryVO())
                .build();

        sampleRepository.getStatusCount().forEach(statusCountVO -> {
            if ("PENDING".equals(statusCountVO.getStatus())) {
                responseVO.setPendingCount(statusCountVO.getCount());
            } else if ("REGISTERED".equals(statusCountVO.getStatus())) {
                responseVO.setTotalCount(statusCountVO.getCount());
            }
        });
        log.info(responseVO.toString());
        return ResponseEntity.ok(responseVO);
    }
}
