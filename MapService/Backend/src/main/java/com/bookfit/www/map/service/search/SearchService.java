package com.bookfit.www.map.service.search;

import com.bookfit.www.map.db.repo.SampleRepository;
import com.bookfit.www.map.dto.search.PatchSearchRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SampleRepository sampleRepository;

    public void updateStatus(PatchSearchRequestDTO request) {
        int updatedCount = sampleRepository.updateStatusByIdAndUserId(
                request.getSampleId(), request.getUserId(), request.getLongitude(), request.getStatus()
        );
        if (updatedCount == 0) {
            throw new EntityNotFoundException("Sample not found or user mismatch");
        }
    }
}
