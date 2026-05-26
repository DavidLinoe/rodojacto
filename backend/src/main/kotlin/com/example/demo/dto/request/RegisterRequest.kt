package com.example.demo.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    val fullName: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Size(min = 6, message = "senha deve ter pelo menos 6 caracteres")
    val password: String,

    @field:NotBlank
    val corporateName: String,

    @field:NotBlank
    val registrationCode: String
)
