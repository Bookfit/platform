package com.bookfit.www.backend.controller

import com.bookfit.www.backend.dto.RequestToken
import com.bookfit.www.backend.utils.jwt.JwtManager
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey

@RestController
class AuthZController(
    @Value("\${jwt.key-id}") private val pbKey: String,
    private val rsaKeyPair: KeyPair,
    private val jwtManager: JwtManager
) {

    @GetMapping("/.well-known/jwks.json", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun jwks(): Mono<Map<String, Any>> {
        val publicKey = rsaKeyPair.public as RSAPublicKey

        val jwk = RSAKey.Builder(publicKey)
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
            jwtManager.generateAccessToken(userId = body.userId, nickname = body.nickname, email = body.email)
        val refreshToken: Map<String, String> =
            jwtManager.generateRefreshToken(userId = body.userId, nickname = body.nickname, email = body.email)


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

        val accessToken =
            jwtManager.generateAccessToken(userId = body.userId, nickname = body.nickname, email = body.email)
        return Mono.just(
            ResponseEntity.ok(
                mapOf(
                    "access" to accessToken.toString()
                )
            )
        )
    }
}
