package com.example.demo.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class DeviceRequest(
    @field:NotBlank(message = "model é obrigatório")
    @field:Size(max = 255)
    val model: String,

    @field:NotBlank(message = "assetTag é obrigatório")
    @field:Size(max = 255)
    val assetTag: String,

    @field:NotNull(message = "organizationId é obrigatório")
    @field:Positive
    val organizationId: Long
)
