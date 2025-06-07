package com.bookfit.www.backend.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * TODO: API 라우팅 설정 입니다.
 * */
@Configuration
class GatewayRoutesConfig {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("auth-service") {
                it.path("/api/oauth/**")
                    .filters { f -> f.stripPrefix(1) }
                    .uri("http://localhost:9010")
            }
            .route("test-service") {
                it.path("/api/test/**")
                    .filters { f -> f.stripPrefix(1) }
                    .uri("http://localhost:9010")
            }
            .build()
    }
}