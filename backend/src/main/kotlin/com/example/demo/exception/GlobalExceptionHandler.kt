package com.example.demo.exception

import com.example.demo.dto.response.ResponseApi
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
