package com.bookfit.www.backend.dto

data class RequestToken(
    val userId: String,
    val nickname: String,
    val email: String,
    val logintype: String
)