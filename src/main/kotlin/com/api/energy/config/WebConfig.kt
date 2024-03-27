package com.api.energy.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "https://matthijsqrcodetattoo.nl/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
    }
}