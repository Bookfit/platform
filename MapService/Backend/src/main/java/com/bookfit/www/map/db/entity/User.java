package com.bookfit.www.map.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Size(max = 50)
    @NotNull
    @Column(name = "social_type", nullable = false, length = 50)
    private String socialType;

    @Size(max = 255)
    @NotNull
    @Column(name = "social_unique_id", nullable = false)
    private String socialUniqueId;

    @Size(max = 320)
    @NotNull
    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "thumbnail", length = Integer.MAX_VALUE)
    private String thumbnail;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "join_at", nullable = false)
    private OffsetDateTime joinAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "refresh_token", length = Integer.MAX_VALUE)
    private String refreshToken;

}