package com.example.demo.dto.response

import com.example.demo.models.enums.AccessLevel
import java.time.LocalDateTime

data class CollaboratorResponse(
    val id: Long,
    val fullName: String,
    val email: String,
    val accessLevel: AccessLevel,
    val organizationId: Long,
    val createdAt: LocalDateTime
)