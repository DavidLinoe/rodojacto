package com.example.demo.service

import com.example.demo.dto.request.DeviceRequest
import com.example.demo.dto.response.DeviceResponse
import com.example.demo.exception.BusinessException
import com.example.demo.exception.NotFoundException
import com.example.demo.models.entity.Device
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
        validate(request)
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
    fun findAll(): List<DeviceResponse> =
        repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun findById(id: Long): DeviceResponse = findEntity(id).toResponse()

    @Transactional
    fun update(id: Long, request: DeviceRequest): DeviceResponse {
        validate(request)
        val existing = findEntity(id)
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
        repository.delete(entity)
    }

    private fun findEntity(id: Long): Device =
        repository.findById(id).orElseThrow { NotFoundException("Device $id not found") }

    private fun validate(request: DeviceRequest) {
        if (request.model.isBlank()) throw BusinessException("model is required")
        if (request.assetTag.isBlank()) throw BusinessException("assetTag is required")
    }

    private fun Device.toResponse() = DeviceResponse(
        id = id,
        model = model,
        assetTag = assetTag,
        organizationId = organization.id,
        createdAt = createdAt
    )
}
