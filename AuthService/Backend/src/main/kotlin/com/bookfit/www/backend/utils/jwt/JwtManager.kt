package com.bookfit.www.backend.utils.jwt

import com.bookfit.www.backend.config.OAuthSecurityConfig
import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.logging.Logger

@Service
class JwtManager(
    @Value("\${jwt.issuer-uri}") private val issuer: String,
    @Value("\${jwt.pub-path}") private val pbKey: String,
    @Value("\${jwt.prb-path}") private val prKey: String,
    @Value("\${jwt.key-id}") private val keyId: String,
    private val oAuthSecurityConfig: OAuthSecurityConfig,
    private val keyPair: KeyPair
) {
    val log = LoggerFactory.getLogger(JwtManager::class.java)
    fun generateAccessToken(userId: String, nickname: String, email: String, logintype: String): Map<String, String> {
        // 🔐 PEM에서 키 불러오기 (최적화 필요, 아래 참고)
        val privateKey = oAuthSecurityConfig.jwtPrivateKey()
        val publicKey = oAuthSecurityConfig.jwtPublicKey()

        val now = Date()
        val expiresAt = Date(now.time + 1000 * 60 * 15) // 15분

        val claims = JWTClaimsSet.Builder()
            .subject(userId)
            .issuer(issuer)
            .issueTime(now)
            .expirationTime(expiresAt)
            .claim("nickname", nickname)
            .claim("email", email)
            .claim("logintype", logintype)
            .build()

        // ✅ 키 변경: keyPair.private → privateKey
        val signer = RSASSASigner(privateKey)

        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(keyId) // 또는 JWK kid 값 (key 식별자), pbKey는 경로이므로 적합하지 않음
            .type(JOSEObjectType.JWT)
            .build()

        val signedJWT = SignedJWT(header, claims)
        signedJWT.sign(signer)

        val token = mapOf(
            "token_type" to "bearer",
            "token" to signedJWT.serialize(),
            "expires_in" to "${expiresAt.time / 1000}"
        )

        log.info("generateAccessToken: {}", token)
        log.info("keyId: {}", keyId)

        return token
    }


    fun generateRefreshToken(userId: String, nickname: String, email: String, logintype: String): Map<String, String> {
        val privateKey = oAuthSecurityConfig.jwtPrivateKey()
        val publicKey = oAuthSecurityConfig.jwtPublicKey()

        val now = Date()
        val expiresAt = Date(now.time + 1000L * 60 * 60 * 24 * 7) // 7일

        val claims = JWTClaimsSet.Builder()
            .subject(userId)
            .issuer(issuer)
            .issueTime(now)
            .expirationTime(expiresAt)
            .claim("nickname", nickname)
            .claim("email", email)
            .claim("logintype", logintype)
            .build()

        val signer = RSASSASigner(privateKey)
        val header = JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(keyId)
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

//    fun loadPublicKeyFromPEMResource(resourcePath: String): RSAPublicKey {
//        val inputStream = Thread.currentThread().contextClassLoader
//            .getResourceAsStream("APP/sms/src_temp/GIPHTTP/keys/public.pem")
//            ?: throw IllegalArgumentException("Resource not found")
//        val keyPEM = inputStream.bufferedReader().use { it.readText() }
//            .replace("-----BEGIN PUBLIC KEY-----", "")
//            .replace("-----END PUBLIC KEY-----", "")
//            .replace("\\s".toRegex(), "")
//        val decoded = Base64.getDecoder().decode(keyPEM)
//        val keySpec = X509EncodedKeySpec(decoded)
//        val keyFactory = KeyFactory.getInstance("RSA")
//        return keyFactory.generatePublic(keySpec) as RSAPublicKey
//    }
//
//    fun loadPrivateKeyFromPEMResource(resourcePath: String): RSAPrivateKey {
//        val inputStream = Thread.currentThread().contextClassLoader
//            .getResourceAsStream("APP/sms/src_temp/GIPHTTP/keys/private.pem")
//            ?: throw IllegalArgumentException("Resource not found")
//
////        val inputStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
//                ?: throw IllegalArgumentException("Resource not found: $resourcePath")
//        val keyPEM = inputStream.bufferedReader().use { it.readText() }
//            .replace("-----BEGIN PRIVATE KEY-----", "")
//            .replace("-----END PRIVATE KEY-----", "")
//            .replace("\\s".toRegex(), "")
//        val decoded = Base64.getDecoder().decode(keyPEM)
//        val keySpec = PKCS8EncodedKeySpec(decoded)
//        val keyFactory = KeyFactory.getInstance("RSA")
//        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
//    }
}
