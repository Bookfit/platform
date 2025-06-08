package com.bookfit.www.backend.service.db

import com.bookfit.www.backend.db.entity.Users
import com.bookfit.www.backend.db.repo.UsersRepository
import com.bookfit.www.backend.service.db.impl.UsersService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository
) : UsersService {
    override fun findByUser(socialType: String, socialId: String): Users? {
        return usersRepository.findByUsers(socialType, socialId)
    }

    @Transactional
    override fun saveUser(user: Users): Users {
        var searchUser: Users? = findByUser(socialType = user.socialType!!, socialId = user.socialUniqueId!!)

        val userToSave = if (searchUser != null) {
            // 기존 값 갱신
            searchUser.apply {
                thumbnail = user.thumbnail
                refreshToken = user.refreshToken
                updatedAt = OffsetDateTime.now(ZoneId.of("Asia/Seoul"))
            }
        } else {
            searchUser = user
        }
        return usersRepository.save(searchUser)
    }
}