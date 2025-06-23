package com.bookfit.www.board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post_file")
public class PostFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postFileId;

    private String fileName;
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}

