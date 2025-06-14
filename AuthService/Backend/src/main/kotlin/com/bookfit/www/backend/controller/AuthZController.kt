package com.bookfit.www.backend.controller

import com.bookfit.www.backend.config.OAuthSecurityConfig
import com.bookfit.www.backend.db.entity.Users
import com.bookfit.www.backend.dto.KakaoUserDTO
import com.bookfit.www.backend.dto.RequestToken
import com.bookfit.www.backend.service.KakaoOAuthService
import com.bookfit.www.backend.service.db.impl.UsersService
import com.bookfit.www.backend.utils.jwt.JwtManager
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URI
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Duration
import java.time.OffsetDateTime

@RestController
class AuthZController(
    @Value("\${jwt.key-id}") private val pbKey: String,
    private val rsaKeyPair: KeyPair,
    private val jwtManager: JwtManager,
    private val kakaoOAuthService: KakaoOAuthService,
    private val usersService: UsersService,
    private val oAuthSecurityConfig: OAuthSecurityConfig
) {
    val log = LoggerFactory.getLogger(AuthZController::class.java)

    @GetMapping("/.well-known/jwks.json", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun jwks(): Mono<Map<String, Any>> {
//        val publicKey = rsaKeyPair.public as RSAPublicKey

//        val publicKey =
//            jwtManager.loadPublicKeyFromPEMResource("src/main/resources/APP/sms/src_temp/GIPHTTP/keys/public.pem")
//        val privateKey =
//            jwtManager.loadPrivateKeyFromPEMResource("src/main/resources/APP/sms/src_temp/GIPHTTP/keys/private.pem")

        val publicKey2 = oAuthSecurityConfig.jwtPublicKey() as RSAPublicKey;
        val privateKey2 = oAuthSecurityConfig.jwtPrivateKey() as RSAPrivateKey;


        val jwk = RSAKey.Builder(publicKey2)
            .privateKey(privateKey2)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID(pbKey) // Public Key
            .build()

        val jwkSet = JWKSet(jwk)

        return Mono.just(jwkSet.toJSONObject())
    }

    /*
    * TODO : 액세스, 리프레시 토큰 정보를 발급 받습니다.
    * */
    @PostMapping(
        "/oauth/token",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun tokenCreate(@ModelAttribute body: RequestToken): Mono<ResponseEntity<Map<String, Map<String, String>>>> {

        val accessToken: Map<String, String> =
            jwtManager.generateAccessToken(
                userId = body.userId,
                nickname = body.nickname,
                email = body.email,
                logintype = body.logintype
            )
        val refreshToken: Map<String, String> =
            jwtManager.generateRefreshToken(
                userId = body.userId,
                nickname = body.nickname,
                email = body.email,
                logintype = body.logintype
            )

        return Mono.just(
            ResponseEntity.ok(
                mapOf(
                    "access" to accessToken,
                    "refresh" to refreshToken
                )
            )
        )
    }

    /*TODO: 액세스 토큰 만료시 재발급을 받습니다.*/
    @PostMapping(
        "/oauth/access-token",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun tokenAccess(@RequestBody body: RequestToken): Mono<ResponseEntity<Map<String, String>>> {

        /*Refresh 토큰 검증 개발 필요*/


        val accessToken =
            jwtManager.generateAccessToken(
                userId = body.userId,
                nickname = body.nickname,
                email = body.email,
                logintype = body.logintype
            )
        return Mono.just(
            ResponseEntity.ok(
                mapOf(
                    "access" to accessToken.toString()
                )
            )
        )
    }

    @GetMapping("/oauth/kakao/callback")
    fun kakaoCallback(
        @RequestParam(required = false) params: Map<String, String>,
        response: ServerHttpResponse
    ): Mono<Void> {
        val error = params["error"]
        val code = params["code"]
        if (!error.isNullOrBlank() || code.isNullOrBlank()) {
            response.statusCode = HttpStatus.FOUND
            response.headers.location = URI.create("/oauth/login")
            return response.setComplete()
        }

        return kakaoOAuthService.processKakaoLogin(code!!)
            .flatMap { kakaoUserDTO ->
                // 토큰 생성
                val accessToken = jwtManager.generateAccessToken(
                    userId = kakaoUserDTO.id.toString(),
                    nickname = kakaoUserDTO.kakao_account.profile!!.nickname.toString(),
                    email = kakaoUserDTO.kakao_account.email!!,
                    logintype = "kakao"
                )

                val refreshToken: Map<String, String> =
                    jwtManager.generateRefreshToken(
                        userId = kakaoUserDTO.id.toString(),
                        nickname = kakaoUserDTO.kakao_account.profile!!.nickname.toString(),
                        email = kakaoUserDTO.kakao_account.email!!,
                        logintype = "kakao"
                    )
                usersService.saveUser(Users().apply {
                    socialType = "kakao"
                    socialUniqueId = kakaoUserDTO.id.toString()
                    email = kakaoUserDTO.kakao_account.email!!
                    thumbnail = kakaoUserDTO.kakao_account.profile.thumbnail_image_url
                    this.refreshToken = refreshToken["token"].toString()
                    joinAt = OffsetDateTime.now()
                    createdAt = OffsetDateTime.now()
                })

                // 쿠키 세팅
                val accessTokenCookie = ResponseCookie
                    .from("access-token", accessToken["token"] ?: "")
                    .httpOnly(false)
                    .secure(false) // 운영환경에서는 true
                    .path("/")
                    .maxAge(Duration.ofSeconds(accessToken["expires_in"]!!.toLong()))
                    .sameSite("Lax")
                    .build()

                val refreshTokenCookie = ResponseCookie
                    .from("refresh-token", refreshToken["token"] ?: "")
                    .httpOnly(true)
                    .secure(false) // 운영환경에서는 true
                    .path("/")
                    .maxAge(Duration.ofSeconds(refreshToken["expires_in"]!!.toLong()))
                    .sameSite("Strict") // CSRF를 더 강하게 방지하기 위해 Strict 추천
                    .build()

                response.addCookie(accessTokenCookie)
                response.addCookie(refreshTokenCookie)

                response.statusCode = HttpStatus.FOUND
                response.headers.location = URI.create("http://localhost:9000/api/map") /*메인으로 리다이렉트*/

                response.setComplete()
            }
            .onErrorResume {
                log.error("error occurred", it)
                // 에러 시 로그인 페이지로
                response.addCookie(
                    ResponseCookie.from("error", "login_failed")
                        .path("/")
                        .maxAge(Duration.ofMinutes(1))
                        .build()
                )
                response.statusCode = HttpStatus.FOUND
                response.headers.location = URI.create("/api/oauth/login")
                response.setComplete()
            }
    }
}
