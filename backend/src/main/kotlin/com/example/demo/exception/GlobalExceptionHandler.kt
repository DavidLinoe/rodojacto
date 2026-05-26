package com.example.demo.exception

import com.example.demo.dto.response.ResponseApi
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<ResponseApi<Nothing>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ResponseApi.error(
                message = ex.message ?: "Recurso não encontrado",
                statusCode = HttpStatus.NOT_FOUND.value(),
                error = "Not Found"
            )
        )

    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(ex: BusinessException): ResponseEntity<ResponseApi<Nothing>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ResponseApi.error(
                message = ex.message ?: "Requisição inválida",
                statusCode = HttpStatus.BAD_REQUEST.value(),
                error = "Bad Request"
            )
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ResponseApi<Nothing>> {
        val errors = ex.bindingResult.fieldErrors.joinToString("; ") {
            "${it.field}: ${it.defaultMessage}"
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ResponseApi.error(
                message = errors.ifBlank { "Dados inválidos" },
                statusCode = HttpStatus.BAD_REQUEST.value(),
                error = "Validation Error"
            )
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(ex: AuthenticationException): ResponseEntity<ResponseApi<Nothing>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ResponseApi.error(
                message = ex.message ?: "Não autenticado",
                statusCode = HttpStatus.UNAUTHORIZED.value(),
                error = "Unauthorized"
            )
        )

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<ResponseApi<Nothing>> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ResponseApi.error(
                message = ex.message ?: "Acesso negado",
                statusCode = HttpStatus.FORBIDDEN.value(),
                error = "Forbidden"
            )
        )

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ResponseApi<Nothing>> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ResponseApi.error(
                message = ex.message ?: "Erro interno do servidor",
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error"
            )
        )
}
