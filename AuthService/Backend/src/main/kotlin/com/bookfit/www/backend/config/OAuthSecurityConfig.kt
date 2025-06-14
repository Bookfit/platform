package com.bookfit.www.backend.config

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.server.SecurityWebFilterChain
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 * TODO: 테스트를 위해 인증/인가를 비허용 처리 합니다.
 * */
@EnableWebFluxSecurity
@Configuration
class OAuthSecurityConfig(
    @Value("\${jwt.jwk-set-uri}") private val jwkSetUri: String,
) {

    @Bean
    fun jwtPrivateKey(): PrivateKey {
        val inputStream = Thread.currentThread().contextClassLoader
            .getResourceAsStream("APP/sms/src_temp/GIPHTTP/keys/private.pem")
            ?: throw IllegalArgumentException("Resource not found")
        val keyPEM = inputStream.bufferedReader().use { it.readText() }
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyPEM)
        val keySpec = PKCS8EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    }

    @Bean
    fun jwtPublicKey(): PublicKey {
        val inputStream = Thread.currentThread().contextClassLoader
            .getResourceAsStream("APP/sms/src_temp/GIPHTTP/keys/public.pem")
            ?: throw IllegalArgumentException("Resource not found")
        val keyPEM = inputStream.bufferedReader().use { it.readText() }
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(keyPEM)
        val keySpec = X509EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers(
                    "/images/**",
                    "/css/**",
                    "/js/**",
                    "/oauth/**",
                    "/.well-known/jwks.json",
                    "/map" /*임시*/
                ).permitAll()
                it.anyExchange().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder()) }
            }
            .formLogin { form ->
                form
                    .loginPage("/login")  // 커스텀 로그인 페이지 경로 지정
                    .disable()            // formLogin 기본 로그인 폼 비활성화 (아래 버튼만 사용)
            }
            .build()
    }

    //<<< SCG 와 인증(로그인)/인가 서버를 분리해야한다., SCG에서 액세스토큰이 만료된 경우 401 응답을 보낸다. , SCG에서 로그인페이지는 인가 절차 없이 접근한다.
    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder {
        println("-----> $jwkSetUri")
        val delegate = NimbusReactiveJwtDecoder.withJwkSetUri("$jwkSetUri").build()

        return ReactiveJwtDecoder { token ->
            delegate.decode(token).doOnNext { jwt ->
                println("JWT kid: ${jwt.headers["kid"]}")
                println("JWT claims: ${jwt.claims}")
            }
        }
    }
}
