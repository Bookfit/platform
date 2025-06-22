package com.bookfit.www.board.service.impl;

import com.bookfit.www.board.service.BoardService;
import com.bookfit.www.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Component("auditorProvider")
public class BoardServiceImpl implements AuditorAware<Long>, BoardService {

    @Override
    public Optional<Long> getCurrentAuditor() {
        // 예: JWT 기반으로 사용자 ID 반환
        // SecurityContext에서 꺼내거나, ThreadLocal 사용
        return Optional.ofNullable(SecurityUtil.getCurrentUserId());
    }
}
