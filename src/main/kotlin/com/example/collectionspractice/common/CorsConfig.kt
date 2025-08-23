package com.example.collectionspractice.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): FilterRegistrationBean<CorsFilter> {
        val config = CorsConfiguration().apply {
            // 필요에 맞게 수정하세요
            allowedOriginPatterns = listOf("http://localhost:5173", "http://localhost:3000", "http://localhost:*")
            addAllowedHeader("*")
            addAllowedMethod("*")
            allowCredentials = true
            maxAge = 3600
        }

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return FilterRegistrationBean(CorsFilter(source)).apply {
            order = 0 // 가장 먼저 적용
        }
    }
}