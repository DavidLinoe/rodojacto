package com.example.demo.controller

import com.example.demo.dto.request.CollaboratorRequest
import com.example.demo.dto.response.CollaboratorResponse
import com.example.demo.service.CollaboratorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/collaborators")
class CollaboratorController(
    private val service: CollaboratorService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CollaboratorRequest): CollaboratorResponse =
        service.create(request)

    @GetMapping
    fun findAll(): List<CollaboratorResponse> = service.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CollaboratorResponse = service.findById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CollaboratorRequest
    ): CollaboratorResponse = service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}