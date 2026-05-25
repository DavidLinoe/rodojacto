package com.example.demo.dto.response

import java.time.LocalDateTime

data class DeviceResponse(
    val id: Long,
    val model: String,
    val assetTag: String,
    val organizationId: Long,
    val createdAt: LocalDateTime
)