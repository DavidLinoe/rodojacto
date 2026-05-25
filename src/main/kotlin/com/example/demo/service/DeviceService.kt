package com.example.demo.service

import com.example.demo.dto.request.DeviceRequest
import com.example.demo.dto.response.DeviceResponse
import org.springframework.stereotype.Service

@Service
class DeviceService {

    fun create(request: DeviceRequest): DeviceResponse = TODO()

    fun findAll(): List<DeviceResponse> = TODO()

    fun findById(id: Long): DeviceResponse = TODO()

    fun update(id: Long, request: DeviceRequest): DeviceResponse = TODO()

    fun delete(id: Long): Unit = TODO()
}