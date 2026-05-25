package com.example.demo.service

import com.example.demo.dto.request.CollaboratorRequest
import com.example.demo.dto.response.CollaboratorResponse
import org.springframework.stereotype.Service

@Service
class CollaboratorService {

    fun create(request: CollaboratorRequest): CollaboratorResponse = TODO()

    fun findAll(): List<CollaboratorResponse> = TODO()

    fun findById(id: Long): CollaboratorResponse = TODO()

    fun update(id: Long, request: CollaboratorRequest): CollaboratorResponse = TODO()

    fun delete(id: Long): Unit = TODO()
}