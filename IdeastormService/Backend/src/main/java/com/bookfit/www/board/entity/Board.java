package com.bookfit.www.board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 100)
    private String name; // ex. 자유게시판, 공지사항

    @Column(nullable = false, unique = true, length = 50)
    private String code; // ex. FREE, NOTICE
}
