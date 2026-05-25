package com.example.demo.repository

import com.example.demo.models.entity.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<Device, Long> {
    fun findAllByOrganizationId(organizationId: Long): List<Device>
}