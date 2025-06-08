package com.bookfit.www.backend.controller

import com.bookfit.www.backend.dto.KakaoAuthDTO
import com.bookfit.www.backend.dto.KakaoUserDTO
import com.bookfit.www.backend.service.KakaoOAuthService
import com.bookfit.www.backend.utils.jwt.JwtManager
import jakarta.validation.constraints.Email
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration


@Controller
class LoginController(
    private val webClient: WebClient,
    @Value("\${kakao.client-id}") private val clientId: String,
    @Value("\${kakao-redirect-uri}") private val redirectUri: String,
    private val kakaoOAuthService: KakaoOAuthService,
    private val jwtManager: JwtManager
) {
    val log = LoggerFactory.getLogger(LoginController::class.java);

    @GetMapping("/oauth/login")
    fun loginRedirect(): Mono<ServerResponse> {
        return ServerResponse.temporaryRedirect(URI.create("/oauth/login")).build();
    }
}