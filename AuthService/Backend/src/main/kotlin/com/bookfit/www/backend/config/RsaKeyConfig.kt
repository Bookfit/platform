package com.bookfit.www.backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyPair
import java.security.KeyPairGenerator

/**
 * TODO : 외부서 JWT 파싱이 필요한 경우 인증 정보를 받아옵니다.
 * */
@Configuration
class RsaKeyConfig(
) {
    @Bean
    fun rsaKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        return keyGen.generateKeyPair()
    }
}
