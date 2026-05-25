package com.example.demo.dto.response

data class LoginResponse(
    val token: String,
    val type: String = "Bearer"
)