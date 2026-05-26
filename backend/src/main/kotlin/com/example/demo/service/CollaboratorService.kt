package com.example.demo.service

import com.example.demo.dto.request.CollaboratorRequest
import com.example.demo.dto.response.CollaboratorResponse
import com.example.demo.exception.BusinessException
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Collaborator
import com.example.demo.models.enums.AccessLevel
import com.example.demo.repository.CollaboratorRepository
import org.springframework.security.access.AccessDeniedException
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
        organizationService.ensureCanAccessOrg(request.organizationId)
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
    fun findAll(): List<CollaboratorResponse> {
        val user = organizationService.currentUser()
        val list = if (user.accessLevel == AccessLevel.MANAGER)
            repository.findAll()
        else
            repository.findAllByOrganizationId(user.organization.id)
        return list.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): CollaboratorResponse {
        val entity = findEntity(id)
        organizationService.ensureCanAccessOrg(entity.organization.id)
        return entity.toResponse()
    }

    @Transactional
    fun update(id: Long, request: CollaboratorRequest): CollaboratorResponse {
        val existing = findEntity(id)
        organizationService.ensureCanAccessOrg(existing.organization.id)
        organizationService.ensureCanAccessOrg(request.organizationId)
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

        if (request.password.isNotBlank() && request.password.length < 6)
            throw BusinessException("password must be at least 6 characters")

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
        organizationService.ensureCanAccessOrg(entity.organization.id)
        if (entity.id == organizationService.currentUser().id)
            throw AccessDeniedException("Não é possível remover a si mesmo")
        repository.delete(entity)
    }

    private fun findEntity(id: Long): Collaborator =
        repository.findById(id).orElseThrow { NotFoundException("Collaborator $id not found") }

    private fun Collaborator.toResponse() = CollaboratorResponse(
        id = id,
        fullName = fullName,
        email = email,
        accessLevel = accessLevel,
        organizationId = organization.id,
        createdAt = createdAt
    )
}
