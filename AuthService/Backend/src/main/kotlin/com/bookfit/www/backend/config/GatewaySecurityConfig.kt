package com.bookfit.www.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.server.SecurityWebFilterChain

/**
 * TODO: 테스트를 위해 인증/인가를 비허용 처리 합니다.
 * */
@EnableWebFluxSecurity
@Configuration
class GatewaySecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers("/oauth/token", "/.well-known/jwks.json","/oauth/access-token").permitAll()
                it.anyExchange().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder()) }
            }
            .build()
    }
//<<< SCG 와 인증(로그인)/인가 서버를 분리해야한다., SCG에서 액세스토큰이 만료된 경우 401 응답을 보낸다. , SCG에서 로그인페이지는 인가 절차 없이 접근한다.
    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder {
        val delegate = NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:8080/.well-known/jwks.json").build()

        return ReactiveJwtDecoder { token ->
            delegate.decode(token).doOnNext { jwt ->
                println("JWT kid: ${jwt.headers["kid"]}")
                println("JWT claims: ${jwt.claims}")
            }
        }
    }

}
