package com.bookfit.www.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
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
            .authorizeExchange { it.anyExchange().permitAll() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .build()
    }
}
