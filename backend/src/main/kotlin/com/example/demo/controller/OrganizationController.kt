package com.example.demo.controller

import com.example.demo.dto.request.OrganizationRequest
import com.example.demo.dto.response.OrganizationResponse
import com.example.demo.dto.response.ResponseApi
import com.example.demo.service.OrganizationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/organizations")
class OrganizationController(
    private val service: OrganizationService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: OrganizationRequest): ResponseApi<OrganizationResponse> =
        ResponseApi.success(
            data = service.create(request),
            message = "Organização criada com sucesso",
            statusCode = HttpStatus.CREATED.value()
        )

    @GetMapping
    fun findAll(): ResponseApi<List<OrganizationResponse>> {
        val list = service.findAll()
        return ResponseApi.success(
            data = list,
            message = "Organizações listadas com sucesso",
            count = list.size
        )
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseApi<OrganizationResponse> =
        ResponseApi.success(
            data = service.findById(id),
            message = "Organização encontrada"
        )

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: OrganizationRequest
    ): ResponseApi<OrganizationResponse> =
        ResponseApi.success(
            data = service.update(id, request),
            message = "Organização atualizada com sucesso"
        )

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseApi<Unit> {
        service.delete(id)
        return ResponseApi.success(
            data = null,
            message = "Organização removida com sucesso"
        )
    }
}
