package com.example.demo.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class OrganizationRequest(
    @field:NotBlank(message = "corporateName é obrigatório")
    @field:Size(max = 255)
    val corporateName: String,

    @field:NotBlank(message = "registrationCode é obrigatório")
    @field:Size(max = 255)
    val registrationCode: String
)
