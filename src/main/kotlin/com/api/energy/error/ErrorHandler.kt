package com.api.energy.error

import com.api.energy.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(value = [IncorrectPasswordException::class, UserNotFoundException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleIncorrectPassword(ex: Exception): ErrorResponse {
        return ErrorResponse(status = HttpStatus.UNAUTHORIZED.value(), description = "Incorrect credentials")
    }

    @ExceptionHandler(value = [DuplicateUserException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDuplicateUsers(ex: DuplicateUserException): ErrorResponse {
        return ErrorResponse(status = HttpStatus.BAD_REQUEST.value(), description = ex.message ?: "Duplicate user")
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIncorrectRequestBody(ex: MethodArgumentNotValidException): ErrorResponse {
        return ErrorResponse(status = HttpStatus.BAD_REQUEST.value(), description = ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(", "))
    }

}