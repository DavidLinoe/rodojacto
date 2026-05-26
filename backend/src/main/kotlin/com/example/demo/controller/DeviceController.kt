package com.example.demo.controller

import com.example.demo.dto.request.DeviceRequest
import com.example.demo.dto.response.DeviceResponse
import com.example.demo.dto.response.ResponseApi
import com.example.demo.service.DeviceService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val service: DeviceService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: DeviceRequest): ResponseApi<DeviceResponse> =
        ResponseApi.success(
            data = service.create(request),
            message = "Dispositivo criado com sucesso",
            statusCode = HttpStatus.CREATED.value()
        )

    @GetMapping
    fun findAll(): ResponseApi<List<DeviceResponse>> {
        val list = service.findAll()
        return ResponseApi.success(
            data = list,
            message = "Dispositivos listados com sucesso",
            count = list.size
        )
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseApi<DeviceResponse> =
        ResponseApi.success(
            data = service.findById(id),
            message = "Dispositivo encontrado"
        )

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: DeviceRequest
    ): ResponseApi<DeviceResponse> =
        ResponseApi.success(
            data = service.update(id, request),
            message = "Dispositivo atualizado com sucesso"
        )

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseApi<Unit> {
        service.delete(id)
        return ResponseApi.success(
            data = null,
            message = "Dispositivo removido com sucesso"
        )
    }
}
