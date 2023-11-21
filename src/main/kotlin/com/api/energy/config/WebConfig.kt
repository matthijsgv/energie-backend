package com.api.energy.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<ContentCachingRequestWrapperFilter> {
        val registrationBean = FilterRegistrationBean(ContentCachingRequestWrapperFilter())
        registrationBean.addUrlPatterns("/api/measurement") // Adjust the URL pattern as needed
        return registrationBean
    }

    @Bean
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor()
    }

    @Bean
    fun interceptorConfigurer(loggingInterceptor: LoggingInterceptor): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(loggingInterceptor)
            }
        }
    }
}