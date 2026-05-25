package com.example.demo.exception

import java.time.LocalDateTime

data class ApiError(
    val status: Int,
    val error: String,
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)