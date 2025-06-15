package com.bookfit.www.map.service.main;

import com.bookfit.www.map.dto.main.MapListStatusCountVO;
import com.bookfit.www.map.dto.main.MapListStatusSummaryVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MapService {
    public ResponseEntity<Object> getBffMain();

    public List<MapListStatusCountVO> getStatusCount();
}
