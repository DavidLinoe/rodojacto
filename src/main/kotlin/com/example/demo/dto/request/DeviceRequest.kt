package com.example.demo.dto.request

data class DeviceRequest(
    val model: String,
    val assetTag: String,
    val organizationId: Long
)