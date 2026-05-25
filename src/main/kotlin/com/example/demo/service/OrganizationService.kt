package com.example.demo.service

import com.example.demo.dto.request.OrganizationRequest
import com.example.demo.dto.response.OrganizationResponse
import org.springframework.stereotype.Service

@Service
class OrganizationService {

    fun create(request: OrganizationRequest): OrganizationResponse = TODO()

    fun findAll(): List<OrganizationResponse> = TODO()

    fun findById(id: Long): OrganizationResponse = TODO()

    fun update(id: Long, request: OrganizationRequest): OrganizationResponse = TODO()

    fun delete(id: Long): Unit = TODO()
}