package com.bookfit.www.backend.service

import com.bookfit.www.backend.dto.KakaoAuthDTO
import com.bookfit.www.backend.dto.KakaoUserDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Mono

@Service
class KakaoOAuthService(
    @Value("\${kakao.client-id}") private val clientId: String,
    @Value("\${kakao-redirect-uri}") private val redirectUri: String
) {
    private val restTemplate = RestTemplate()

    fun processKakaoLogin(code: String): Mono<KakaoUserDTO> {
        val token = requestKakaoToken(code)
        return Mono.just(requestKakaoUserInfo(token.access_token))
    }

    private fun requestKakaoToken(code: String): KakaoAuthDTO {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
            add("code", code)
        }

        val request = HttpEntity(body, headers)

        val response = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            request,
            KakaoAuthDTO::class.java
        )

        return response.body ?: throw IllegalStateException("Failed to get token from Kakao")
    }

    private fun requestKakaoUserInfo(accessToken: String): KakaoUserDTO {
//        println("accessToken: $accessToken")
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            setBearerAuth(accessToken)
        }

        val request = HttpEntity(null, headers)

        val response = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            request,
            KakaoUserDTO::class.java
        )


        return response.body ?: throw IllegalStateException("Failed to get user info from Kakao")
    }
}