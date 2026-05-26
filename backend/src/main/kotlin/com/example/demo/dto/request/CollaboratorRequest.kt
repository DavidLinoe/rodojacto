package com.example.demo.dto.request

import com.example.demo.models.enums.AccessLevel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class CollaboratorRequest(
    @field:NotBlank(message = "fullName é obrigatório")
    @field:Size(max = 255)
    val fullName: String,

    @field:NotBlank(message = "email é obrigatório")
    @field:Email(message = "email inválido")
    val email: String,

    val password: String = "",

    @field:NotNull(message = "accessLevel é obrigatório")
    val accessLevel: AccessLevel,

    @field:NotNull(message = "organizationId é obrigatório")
    @field:Positive
    val organizationId: Long
)
