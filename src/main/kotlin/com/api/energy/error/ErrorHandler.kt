package com.api.energy.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(value = [IncorrectPasswordException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleIncorrectPassword(ex : Exception): String {
        return "HOER!"
    }
}