package com.bookfit.www.backend.service.db.impl

import com.bookfit.www.backend.db.entity.User
import reactor.core.publisher.Mono

interface UsersService {
    fun findByUser(socialType: String, socialId: String): User?
    fun saveUser(user: User): User
}