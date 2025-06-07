package com.bookfit.www.backend.utils.jwt

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.util.*

@Service
class JwtManager(
    @Value("\${jwt.issuer-uri}") private val issuer: String,
    @Value("\${jwt.key-id}") private val pbKey: String,
    private val keyPair: KeyPair
) {
    fun generateAccessToken(userId: String, nickname: String, email: String): Map<String, String> {
        val now = Date()
        val expiresAt = Date(now.time + 1000 * 60 * 15) // 15분

        val claims = JWTClaimsSet.Builder()
            .subject(userId)
            .issuer(issuer)
            .issueTime(now)
            .expirationTime(expiresAt)
            .claim("nickname", nickname)
            .claim("email", email)
            .build()

        val signer = RSASSASigner(keyPair.private)
        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(pbKey)
            .type(JOSEObjectType.JWT)
            .build()

        val signedJWT = SignedJWT(header, claims)
        signedJWT.sign(signer)

        return mapOf(
            "token_type" to "bearer",
            "token" to signedJWT.serialize(),
            "expires_in" to "${expiresAt.time / 1000}",
        )
    }

    fun generateRefreshToken(userId: String, nickname: String, email: String): Map<String, String> {
        val now = Date()
        val expiresAt = Date(now.time + 1000L * 60 * 60 * 24 * 7) // 7일

        val claims = JWTClaimsSet.Builder()
            .subject(userId)
            .issuer(issuer)
            .issueTime(now)
            .expirationTime(expiresAt)
            .claim("nickname", nickname)
            .claim("email", email)
            .build()

        val signer = RSASSASigner(keyPair.private)
        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(pbKey)
            .type(JOSEObjectType.JWT)
            .build()

        val signedJWT = SignedJWT(header, claims)
        signedJWT.sign(signer)

        return mapOf(
            "token_type" to "bearer",
            "token" to signedJWT.serialize(),
            "expires_in" to "${expiresAt.time / 1000}",
        )
    }
}
