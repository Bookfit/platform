package com.bookfit.www.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver
import java.util.*

@Configuration
class LocaleConfig {

    @Bean
    fun localeContextResolver(): AcceptHeaderLocaleContextResolver {
        val resolver = AcceptHeaderLocaleContextResolver()
        resolver.defaultLocale = Locale.KOREA
        return resolver
    }
}



