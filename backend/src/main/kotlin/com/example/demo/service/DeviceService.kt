package com.example.demo.service

import com.example.demo.dto.request.DeviceRequest
import com.example.demo.dto.response.DeviceResponse
import com.example.demo.exception.BusinessException
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Device
import com.example.demo.models.enums.AccessLevel
import com.example.demo.repository.DeviceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceService(
    private val repository: DeviceRepository,
    private val organizationService: OrganizationService
) {

    @Transactional
    fun create(request: DeviceRequest): DeviceResponse {
        organizationService.ensureCanAccessOrg(request.organizationId)
        repository.findByAssetTag(request.assetTag)?.let {
            throw BusinessException("assetTag already in use")
        }
        val organization = organizationService.findEntity(request.organizationId)
        val entity = Device(
            model = request.model.trim(),
            assetTag = request.assetTag.trim(),
            organization = organization
        )
        return repository.save(entity).toResponse()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<DeviceResponse> {
        val user = organizationService.currentUser()
        val list = if (user.accessLevel != AccessLevel.OPERATOR)
            repository.findAll()
        else
            repository.findAllByOrganizationId(user.organization.id)
        return list.map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): DeviceResponse {
        val entity = findEntity(id)
        organizationService.ensureCanAccessOrg(entity.organization.id)
        return entity.toResponse()
    }

    @Transactional
    fun update(id: Long, request: DeviceRequest): DeviceResponse {
        val existing = findEntity(id)
        organizationService.ensureCanAccessOrg(existing.organization.id)
        organizationService.ensureCanAccessOrg(request.organizationId)
        val tag = request.assetTag.trim()
        if (tag != existing.assetTag) {
            repository.findByAssetTag(tag)?.let {
                throw BusinessException("assetTag already in use")
            }
        }
        val organization =
            if (request.organizationId != existing.organization.id)
                organizationService.findEntity(request.organizationId)
            else existing.organization

        val updated = existing.copy(
            model = request.model.trim(),
            assetTag = tag,
            organization = organization
        )
        return repository.save(updated).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        val entity = findEntity(id)
        organizationService.ensureCanAccessOrg(entity.organization.id)
        repository.delete(entity)
    }

    private fun findEntity(id: Long): Device =
        repository.findById(id).orElseThrow { NotFoundException("Device $id not found") }

    private fun Device.toResponse() = DeviceResponse(
        id = id,
        model = model,
        assetTag = assetTag,
        organizationId = organization.id,
        createdAt = createdAt
    )
}
