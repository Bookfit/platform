package com.bookfit.www.map.service.search;

import com.bookfit.www.map.db.entity.QSample;
import com.bookfit.www.map.db.entity.QUser;
import com.bookfit.www.map.db.entity.Sample;
import com.bookfit.www.map.db.repo.SampleRepository;
import com.bookfit.www.map.dto.search.*;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SampleRepository sampleRepository;

    public List<GetSearchSampleResponseDTO> searchSamples(Pageable pageable) {
        if (pageable == null) {
            return sampleRepository.findAll().stream()
                    .map(this::convertToDto)
                    .toList();
        } else {
            return sampleRepository.findSamplesWithPaging(pageable)
                    .map(this::convertToDto)
                    .toList();
        }
    }

    private GetSearchSampleResponseDTO convertToDto(Sample s) {
        GetSearchSampleResponseDTO dto = new GetSearchSampleResponseDTO();
        dto.setId(s.getSampleId());
        dto.setUserId(s.getUser().getUserId());
        dto.setLoginType(s.getUser().getSocialType());
        dto.setName(s.getName());
        dto.setAddress(s.getAddress());
        dto.setDetailAddress(s.getDetailAddress());
        dto.setLat(s.getLocation() != null ? s.getLocation().getY() : null);
        dto.setLon(s.getLocation() != null ? s.getLocation().getX() : null);
        dto.setWeekdayHours(s.getWeekdayHours());
        dto.setWeekendHours(s.getWeekendHours());
        dto.setDescription(s.getDescription());
        dto.setStatus(s.getStatus());

        dto.setCategories(s.getCategories().stream()
                .map(c -> new CategoryDTO(c.getCode(), c.getName()))
                .collect(Collectors.toList()));

        dto.setFacilities(s.getFacilities().stream()
                .map(f -> new FacilitiesDTO(f.getCode(), f.getName()))
                .collect(Collectors.toList()));

        return dto;
    }

    public void updateStatus(PatchSearchRequestDTO request) {
        int updatedCount = sampleRepository.updateStatusByIdAndUserId(
                request.getSampleId(), request.getUserId(), request.getLongitude(), request.getStatus()
        );
        if (updatedCount == 0) {
            throw new EntityNotFoundException("Sample not found or user mismatch");
        }
    }
}
