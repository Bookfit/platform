package com.bookfit.www.backend.config

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class NotFoundFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return chain.filter(exchange).onErrorResume { ex ->
            if (exchange.response.isCommitted) {
                return@onErrorResume Mono.error(ex)
            }
            exchange.response.statusCode = HttpStatus.NOT_FOUND
            val buffer = exchange.response.bufferFactory().wrap(
                "{\"message\":\"Route Not Found\"}".toByteArray(StandardCharsets.UTF_8)
            )
            exchange.response.headers.contentType = MediaType.APPLICATION_JSON
            exchange.response.writeWith(Mono.just(buffer))
        }
    }

    override fun getOrder(): Int = -1
}