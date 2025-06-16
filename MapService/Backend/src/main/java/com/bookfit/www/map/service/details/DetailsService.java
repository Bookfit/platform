package com.bookfit.www.map.service.details;

import com.bookfit.www.map.db.repo.CategoryRepository;
import com.bookfit.www.map.db.repo.SampleFacilityRepository;
import com.bookfit.www.map.dto.details.GetDetailsResponseDTO;
import com.bookfit.www.map.dto.search.CategoryVO;
import com.bookfit.www.map.dto.search.FacilitiesVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailsService {

    private final CategoryRepository categoryRepository;
    private final SampleFacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public GetDetailsResponseDTO getAllCategoriesAndFacilities() {
        GetDetailsResponseDTO dto = new GetDetailsResponseDTO();

        dto.setCategories(
                categoryRepository.findAll().stream()
                        .map(c -> new CategoryVO(c.getCode(), c.getName()))
                        .collect(Collectors.toList())
        );

        dto.setFacilities(
                facilityRepository.findAll().stream()
                        .map(f -> new FacilitiesVO(f.getCode(), f.getName()))
                        .collect(Collectors.toList())
        );

        return dto;
    }
}

