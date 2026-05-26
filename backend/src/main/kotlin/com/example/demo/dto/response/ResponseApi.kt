package com.example.demo.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseApi<T>(
    val statusCode: Int,
    val message: String,
    val loading: Boolean = false,
    val error: String? = null,
    val data: T?,
    val count: Int? = null
) {
    companion object {
        fun <T> success(
            data: T?,
            message: String = "OK",
            statusCode: Int = 200,
            count: Int? = null
        ): ResponseApi<T> = ResponseApi(
            statusCode = statusCode,
            message = message,
            loading = false,
            error = null,
            data = data,
            count = count
        )

        fun <T> error(
            message: String,
            statusCode: Int,
            error: String? = message,
            data: T? = null
        ): ResponseApi<T> = ResponseApi(
            statusCode = statusCode,
            message = message,
            loading = false,
            error = error,
            data = data
        )
    }
}
