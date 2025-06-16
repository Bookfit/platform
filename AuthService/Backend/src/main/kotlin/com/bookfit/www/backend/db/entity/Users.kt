package com.bookfit.www.backend.db.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    public var userId: Int? = null

    @Column(name = "social_type", nullable = false, length = 50)
    public var socialType: @Size(max = 50) @NotNull String? = null

    @Column(name = "social_unique_id", nullable = false)
    public var socialUniqueId: @Size(max = 255) @NotNull String? = null

    @Column(name = "email", nullable = false, length = 320)
    public var email: @Size(max = 320) @NotNull String? = null

    @Column(name = "thumbnail", length = Int.MAX_VALUE)
    public var thumbnail: String? = null

    @ColumnDefault("now()")
    @Column(name = "join_at", nullable = false)
    public var joinAt: @NotNull OffsetDateTime? = null

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    public var createdAt: @NotNull OffsetDateTime? = null

    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    public var updatedAt: @NotNull OffsetDateTime? = null

    @Column(name = "refresh_token", length = Int.MAX_VALUE)
    public var refreshToken: String? = null
}