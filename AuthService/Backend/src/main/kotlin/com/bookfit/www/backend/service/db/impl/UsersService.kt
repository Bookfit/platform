package com.bookfit.www.backend.service.db.impl

import com.bookfit.www.backend.db.entity.Users
import reactor.core.publisher.Mono

interface UsersService {
    fun findByUser(socialType: String, socialId: String): Users?
    fun saveUser(user: Users): Users
}