package com.api.energy.model.response

import java.time.LocalDateTime

data class ErrorResponse(
        val status: Int,
        val timeStamp: LocalDateTime = LocalDateTime.now(),
        val description: String
)
