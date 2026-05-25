package com.example.demo.dto.request

import com.example.demo.models.enums.AccessLevel

data class CollaboratorRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val accessLevel: AccessLevel,
    val organizationId: Long
)