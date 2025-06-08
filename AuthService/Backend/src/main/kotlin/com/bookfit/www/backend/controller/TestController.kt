package com.bookfit.www.backend.controller

import com.bookfit.www.backend.db.entity.Users
import com.bookfit.www.backend.service.db.impl.UsersService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val usersService: UsersService
) {
    @GetMapping("/test")
    fun test(): String {
        println(
            usersService.findByUser("kakao", "1").toString()
        )
        return "Hello World"
    }

    @GetMapping("/map")
    fun mapTest(): String {
        return "공간정보공유페이지 이동"
    }

    @GetMapping("")
    fun mainTest(): String {
        return "Main Hello"
    }
}