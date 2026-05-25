package com.example.demo.dto.request

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val corporateName: String,
    val registrationCode: String
)
