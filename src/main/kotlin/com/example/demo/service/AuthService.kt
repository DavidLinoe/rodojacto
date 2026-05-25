package com.example.demo.service

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.response.LoginResponse
import org.springframework.stereotype.Service

@Service
class AuthService {

    fun login(request: LoginRequest): LoginResponse = TODO()
}