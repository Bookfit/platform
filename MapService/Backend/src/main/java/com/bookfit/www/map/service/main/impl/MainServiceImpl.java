package com.bookfit.www.map.service.main.impl;

import com.bookfit.www.map.db.entity.Category;
import com.bookfit.www.map.db.entity.Sample;
import com.bookfit.www.map.db.entity.User;
import com.bookfit.www.map.db.repo.CategoryRepository;
import com.bookfit.www.map.db.repo.SampleRepository;
import com.bookfit.www.map.db.repo.UserRepository;
import com.bookfit.www.map.dto.main.MapCategoryVO;
import com.bookfit.www.map.dto.main.MapListStatusCountVO;
import com.bookfit.www.map.dto.main.MapListStatusSummaryVO;
import com.bookfit.www.map.dto.main.PostMainRequestDTO;
import com.bookfit.www.map.service.main.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        categoryRepository.findAllMappedCategoryVO().stream().map(categoryVO -> {
            return MapCategoryVO.builder().code(categoryVO.getCode()).name(categoryVO.getName()).build();
        }).collect(Collectors.toList());

        MapListStatusSummaryVO responseVO = MapListStatusSummaryVO.builder()
                .categories(categoryRepository.findAllMappedCategoryVO().stream().map(categoryVO -> {
                    return MapCategoryVO.builder().code(categoryVO.getCode()).name(categoryVO.getName()).build();
                }).collect(Collectors.toList()))
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

    @Override
    public ResponseEntity<Object> saveBffMain(PostMainRequestDTO postMainRequestDTO) {

        List<String> selectCategory = postMainRequestDTO.getCategories().stream().map(MapCategoryVO::getCode).collect(Collectors.toList());

        try {
            User user = userRepository.findUserBySocialUniqueIdAndSocialType(
                    postMainRequestDTO.getUserId().toString(),
                    postMainRequestDTO.getLoginType()
            ).orElseThrow(() ->
                    new RuntimeException("User not found")
            );

            sampleRepository.save(Sample.builder()
                    .user(user)
                    .name(postMainRequestDTO.getName())
                    .status("PENDING")
                    .description(postMainRequestDTO.getDescription())
                    .categories(categoryRepository.findByCodeIn(selectCategory))
                    .build());
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            } else {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(e.getMessage());
            }
        }
        return ResponseEntity.ok("");
    }
}
