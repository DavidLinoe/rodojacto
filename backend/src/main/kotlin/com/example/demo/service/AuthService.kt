package com.example.demo.service

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.LoginResponse
import com.example.demo.exception.BusinessException
import com.example.demo.models.entity.Collaborator
import com.example.demo.models.entity.Organization
import com.example.demo.models.enums.AccessLevel
import com.example.demo.repository.CollaboratorRepository
import com.example.demo.repository.OrganizationRepository
import com.example.demo.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val collaboratorRepository: CollaboratorRepository,
    private val organizationRepository: OrganizationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun login(request: LoginRequest): LoginResponse {
        if (request.email.isBlank() || request.password.isBlank())
            throw BusinessException("email and password are required")

        val collaborator = collaboratorRepository.findByEmail(request.email.trim().lowercase())
            ?: throw BusinessException("Invalid credentials")

        if (!passwordEncoder.matches(request.password, collaborator.password))
            throw BusinessException("Invalid credentials")

        return LoginResponse(token = jwtService.generateToken(collaborator))
    }

    @Transactional
    fun register(request: RegisterRequest): LoginResponse {
        if (request.email.isBlank() || request.password.isBlank() ||
            request.fullName.isBlank() || request.corporateName.isBlank() ||
            request.registrationCode.isBlank()
        ) throw BusinessException("all fields are required")

        val email = request.email.trim().lowercase()
        if (collaboratorRepository.findByEmail(email) != null)
            throw BusinessException("email already in use")

        val organization = organizationRepository.save(
            Organization(
                corporateName = request.corporateName.trim(),
                registrationCode = request.registrationCode.trim()
            )
        )

        val collaborator = collaboratorRepository.save(
            Collaborator(
                fullName = request.fullName.trim(),
                email = email,
                password = passwordEncoder.encode(request.password)!!,
                accessLevel = AccessLevel.ADMIN,
                organization = organization
            )
        )

        return LoginResponse(token = jwtService.generateToken(collaborator))
    }
}
