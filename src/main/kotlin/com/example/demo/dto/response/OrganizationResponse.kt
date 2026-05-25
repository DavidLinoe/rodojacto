package com.example.demo.dto.response

import java.time.LocalDateTime

data class OrganizationResponse(
    val id: Long,
    val corporateName: String,
    val registrationCode: String,
    val createdAt: LocalDateTime
)