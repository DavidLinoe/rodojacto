package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.LoginResponse
import com.example.demo.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse =
        authService.login(request)

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterRequest): LoginResponse =
        authService.register(request)
}
