package com.example.demo.controller

import com.example.demo.dto.request.CollaboratorRequest
import com.example.demo.dto.response.CollaboratorResponse
import com.example.demo.dto.response.ResponseApi
import com.example.demo.service.CollaboratorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/collaborators")
class CollaboratorController(
    private val service: CollaboratorService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CollaboratorRequest): ResponseApi<CollaboratorResponse> =
        ResponseApi.success(
            data = service.create(request),
            message = "Colaborador criado com sucesso",
            statusCode = HttpStatus.CREATED.value()
        )

    @GetMapping
    fun findAll(): ResponseApi<List<CollaboratorResponse>> {
        val list = service.findAll()
        return ResponseApi.success(
            data = list,
            message = "Colaboradores listados com sucesso",
            count = list.size
        )
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseApi<CollaboratorResponse> =
        ResponseApi.success(
            data = service.findById(id),
            message = "Colaborador encontrado"
        )

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CollaboratorRequest
    ): ResponseApi<CollaboratorResponse> =
        ResponseApi.success(
            data = service.update(id, request),
            message = "Colaborador atualizado com sucesso"
        )

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseApi<Unit> {
        service.delete(id)
        return ResponseApi.success(
            data = null,
            message = "Colaborador removido com sucesso"
        )
    }
}
