package com.bookfit.www.board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "board_code", nullable = false, length = 50)
    private String boardCode;

    private int commentCount;
}

