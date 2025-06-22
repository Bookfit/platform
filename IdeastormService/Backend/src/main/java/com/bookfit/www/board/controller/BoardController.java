package com.bookfit.www.board.controller;

import com.bookfit.www.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/main")
@Tag(name = "/board/main", description = "메인화면 페이지")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

}
