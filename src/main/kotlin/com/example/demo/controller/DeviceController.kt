package com.example.demo.controller

import com.example.demo.dto.request.DeviceRequest
import com.example.demo.dto.response.DeviceResponse
import com.example.demo.service.DeviceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val service: DeviceService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: DeviceRequest): DeviceResponse =
        service.create(request)

    @GetMapping
    fun findAll(): List<DeviceResponse> = service.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): DeviceResponse = service.findById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: DeviceRequest
    ): DeviceResponse = service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}