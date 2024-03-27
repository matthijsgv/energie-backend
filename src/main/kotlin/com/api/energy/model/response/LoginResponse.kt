package com.api.energy.model.response

data class LoginResponse(
        val userId: String,
        val username: String,
        val name: String,
        val role: String,
        val token: String
)