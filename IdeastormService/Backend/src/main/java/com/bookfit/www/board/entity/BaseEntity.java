package com.bookfit.www.board.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    @Column(name = "created_id", updatable = false)
    protected Long createdId;

    @Column(name = "updated_id")
    protected Long updatedId;

    @Column(name = "is_deleted", nullable = false)
    protected boolean isDeleted = false;
}

