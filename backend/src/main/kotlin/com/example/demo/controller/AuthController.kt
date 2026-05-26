package com.example.demo.controller

import com.example.demo.dto.request.LoginRequest
import com.example.demo.dto.request.RegisterRequest
import com.example.demo.dto.response.LoginResponse
import com.example.demo.dto.response.ResponseApi
import com.example.demo.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseApi<LoginResponse> =
        ResponseApi.success(
            data = authService.login(request),
            message = "Login realizado com sucesso",
            statusCode = HttpStatus.OK.value()
        )

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseApi<LoginResponse> =
        ResponseApi.success(
            data = authService.register(request),
            message = "Cadastro realizado com sucesso",
            statusCode = HttpStatus.CREATED.value()
        )
}
