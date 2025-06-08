package com.bookfit.www.backend.db.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.ToString
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime
import java.time.ZoneId

@Entity
@Table(name = "users")
@ToString
open class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "social_type", nullable = false, length = 50)
    open var socialType: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "social_unique_id", nullable = false)
    open var socialUniqueId: String? = null

    @Size(max = 320)
    @NotNull
    @Column(name = "email", nullable = false, length = 320)
    open var email: String? = null

    @Column(name = "thumbnail", length = Integer.MAX_VALUE)
    open var thumbnail: String? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "join_at", nullable = false)
    open var joinAt: OffsetDateTime? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    open var createdAt: OffsetDateTime? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    open var updatedAt: OffsetDateTime? = OffsetDateTime.now(ZoneId.of("Asia/Seoul"))

    @Column(name = "refresh_token", length = Integer.MAX_VALUE)
    open var refreshToken: String? = null
}