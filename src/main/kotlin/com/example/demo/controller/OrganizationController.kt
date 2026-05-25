package com.example.demo.controller

import com.example.demo.dto.request.OrganizationRequest
import com.example.demo.dto.response.OrganizationResponse
import com.example.demo.service.OrganizationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/organizations")
class OrganizationController(
    private val service: OrganizationService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: OrganizationRequest): OrganizationResponse =
        service.create(request)

    @GetMapping
    fun findAll(): List<OrganizationResponse> = service.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): OrganizationResponse = service.findById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: OrganizationRequest
    ): OrganizationResponse = service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}