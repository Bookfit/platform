package com.bookfit.www.backend.db.repo

import com.bookfit.www.backend.db.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UsersRepository : JpaRepository<Users, Int> {
    @Query("select u from Users u where u.socialType=:socialType AND u.socialUniqueId=:socialId")
    fun findByUsers(socialType: String, socialId: String): Users?
}