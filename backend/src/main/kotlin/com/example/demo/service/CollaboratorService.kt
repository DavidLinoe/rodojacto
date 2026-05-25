package com.example.demo.service

import com.example.demo.dto.request.CollaboratorRequest
import com.example.demo.dto.response.CollaboratorResponse
import com.example.demo.exception.BusinessException
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Collaborator
import com.example.demo.repository.CollaboratorRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CollaboratorService(
    private val repository: CollaboratorRepository,
    private val organizationService: OrganizationService,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun create(request: CollaboratorRequest): CollaboratorResponse {
        validate(request, isUpdate = false)
        repository.findByEmail(request.email)?.let {
            throw BusinessException("Email already in use")
        }
        val organization = organizationService.findEntity(request.organizationId)
        val entity = Collaborator(
            fullName = request.fullName.trim(),
            email = request.email.trim().lowercase(),
            password = passwordEncoder.encode(request.password)!!,
            accessLevel = request.accessLevel,
            organization = organization
        )
        return repository.save(entity).toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<CollaboratorResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun findById(id: Long): CollaboratorResponse = findEntity(id).toResponse()

    @Transactional
    fun update(id: Long, request: CollaboratorRequest): CollaboratorResponse {
        validate(request, isUpdate = true)
        val existing = findEntity(id)
        val emailNormalized = request.email.trim().lowercase()
        if (emailNormalized != existing.email) {
            repository.findByEmail(emailNormalized)?.let {
                throw BusinessException("Email already in use")
            }
        }
        val organization =
            if (request.organizationId != existing.organization.id)
                organizationService.findEntity(request.organizationId)
            else existing.organization

        val password =
            if (request.password.isNotBlank()) passwordEncoder.encode(request.password)!!
            else existing.password

        val updated = existing.copy(
            fullName = request.fullName.trim(),
            email = emailNormalized,
            password = password,
            accessLevel = request.accessLevel,
            organization = organization
        )
        return repository.save(updated).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        val entity = findEntity(id)
        repository.delete(entity)
    }

    private fun findEntity(id: Long): Collaborator =
        repository.findById(id).orElseThrow { NotFoundException("Collaborator $id not found") }

    private fun validate(request: CollaboratorRequest, isUpdate: Boolean) {
        if (request.fullName.isBlank()) throw BusinessException("fullName is required")
        if (request.email.isBlank() || !request.email.contains("@"))
            throw BusinessException("valid email is required")
        if (!isUpdate && request.password.length < 6)
            throw BusinessException("password must be at least 6 characters")
        if (isUpdate && request.password.isNotBlank() && request.password.length < 6)
            throw BusinessException("password must be at least 6 characters")
    }

    private fun Collaborator.toResponse() = CollaboratorResponse(
        id = id,
        fullName = fullName,
        email = email,
        accessLevel = accessLevel,
        organizationId = organization.id,
        createdAt = createdAt
    )
}
