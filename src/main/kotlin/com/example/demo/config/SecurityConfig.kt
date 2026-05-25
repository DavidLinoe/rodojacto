package com.example.demo.config

import com.example.demo.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    // TODO: configurar SecurityFilterChain, PasswordEncoder (BCrypt),
    // AuthenticationManager, e liberar /api/auth/** + swagger
}