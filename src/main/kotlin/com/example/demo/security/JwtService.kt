package com.example.demo.security

import com.example.demo.models.entity.Collaborator
import org.springframework.stereotype.Service

@Service
class JwtService {

    fun generateToken(collaborator: Collaborator): String = TODO()

    fun extractEmail(token: String): String = TODO()

    fun isValid(token: String): Boolean = TODO()
}