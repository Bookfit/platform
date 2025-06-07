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
            .route(
                "auth-service"
            ) { r: PredicateSpec ->
                r.path("/api/auth/**")
                    .filters { f: GatewayFilterSpec -> f.stripPrefix(1) }
                    .uri("http://localhost:8080")
            }
            .route(
                "jwks-service"
            ) { r: PredicateSpec ->
                r.path("/.well-known/jwks.json")
                    .uri("http://localhost:8080")
            }
            .route(
                "test-service"
            ) { r: PredicateSpec ->
                r.path("/api/test/**")
                    .filters { f: GatewayFilterSpec -> f.stripPrefix(1) }
                    .uri("http://localhost:8080")
            }
            .build()
    }
}