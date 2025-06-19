package com.bookfit.www.backend.config.routes

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


/**
 * TODO: API 라우팅 설정 입니다.
 * */
@Configuration
@Profile("test") // test 프로필이 아닐 때
class TestRoutesConfig {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("auth-service") {
                it.path("/api/oauth/**")
                    .filters { f -> f.stripPrefix(1) }
//                    .uri("http://localhost:9010")
                    .uri("http://kimbaro.iptime.org:9010")
            }
            .route("map-service") {
                it.path("/api/map/**")
                    .filters { f -> f.stripPrefix(1) }
//                    .uri("http://localhost:9010")
                    .uri("http://kimbaro.iptime.org:9020")
            }
            .route("test-service") {
                it.path("/api/test/**")
                    .filters { f -> f.stripPrefix(1) }
                    .uri("http://kimbaro.iptime.org:9010")
            }
            .route("main-page") {
                it.path("/")
                    .uri("http://kimbaro.iptime.org:9010/oauth/login")
            }
            .build()
    }
}