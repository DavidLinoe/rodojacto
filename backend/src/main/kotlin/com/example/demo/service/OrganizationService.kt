package com.example.demo.service

import com.example.demo.dto.request.OrganizationRequest
import com.example.demo.dto.response.OrganizationResponse
import com.example.demo.exception.BusinessException
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Organization
import com.example.demo.repository.OrganizationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrganizationService(
    private val repository: OrganizationRepository
) {

    @Transactional
    fun create(request: OrganizationRequest): OrganizationResponse {
        validate(request)
        val entity = Organization(
            corporateName = request.corporateName.trim(),
            registrationCode = request.registrationCode.trim()
        )
        return repository.save(entity).toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<OrganizationResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun findById(id: Long): OrganizationResponse = findEntity(id).toResponse()

    @Transactional
    fun update(id: Long, request: OrganizationRequest): OrganizationResponse {
        validate(request)
        val existing = findEntity(id)
        val updated = existing.copy(
            corporateName = request.corporateName.trim(),
            registrationCode = request.registrationCode.trim()
        )
        return repository.save(updated).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        val entity = findEntity(id)
        repository.delete(entity)
    }

    fun findEntity(id: Long): Organization =
        repository.findById(id).orElseThrow { NotFoundException("Organization $id not found") }

    private fun validate(request: OrganizationRequest) {
        if (request.corporateName.isBlank()) throw BusinessException("corporateName is required")
        if (request.registrationCode.isBlank()) throw BusinessException("registrationCode is required")
    }

    private fun Organization.toResponse() = OrganizationResponse(
        id = id,
        corporateName = corporateName,
        registrationCode = registrationCode,
        createdAt = createdAt
    )
}
