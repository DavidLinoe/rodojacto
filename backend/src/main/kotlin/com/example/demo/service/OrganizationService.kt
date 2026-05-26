package com.example.demo.service

import com.example.demo.dto.request.OrganizationRequest
import com.example.demo.dto.response.OrganizationResponse
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Collaborator
import com.example.demo.models.entity.Organization
import com.example.demo.models.enums.AccessLevel
import com.example.demo.repository.OrganizationRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrganizationService(
    private val repository: OrganizationRepository
) {

    @Transactional
    fun create(request: OrganizationRequest): OrganizationResponse {
        requireManager()
        val entity = Organization(
            corporateName = request.corporateName.trim(),
            registrationCode = request.registrationCode.trim()
        )
        return repository.save(entity).toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<OrganizationResponse> {
        val user = currentUser()
        return listOf(findEntity(user.organization.id).toResponse())
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): OrganizationResponse {
        ensureCanAccessOrg(id)
        return findEntity(id).toResponse()
    }

    @Transactional
    fun update(id: Long, request: OrganizationRequest): OrganizationResponse {
        requireManager()
        val existing = findEntity(id)
        val updated = existing.copy(
            corporateName = request.corporateName.trim(),
            registrationCode = request.registrationCode.trim()
        )
        return repository.save(updated).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        requireManager()
        if (currentUser().organization.id == id)
            throw AccessDeniedException("Não é possível remover a organização à qual você pertence")
        val entity = findEntity(id)
        repository.delete(entity)
    }

    fun findEntity(id: Long): Organization =
        repository.findById(id).orElseThrow { NotFoundException("Organization $id not found") }

    fun currentUser(): Collaborator =
        SecurityContextHolder.getContext().authentication?.principal as? Collaborator
            ?: throw AccessDeniedException("Não autenticado")

    fun ensureCanAccessOrg(orgId: Long) {
        val user = currentUser()
        if (user.accessLevel == AccessLevel.OPERATOR && user.organization.id != orgId)
            throw AccessDeniedException("Acesso negado à organização $orgId")
    }

    private fun requireManager() {
        if (currentUser().accessLevel == AccessLevel.OPERATOR)
            throw AccessDeniedException("OPERATOR não pode executar esta operação")
    }

    private fun Organization.toResponse() = OrganizationResponse(
        id = id,
        corporateName = corporateName,
        registrationCode = registrationCode,
        createdAt = createdAt
    )
}
