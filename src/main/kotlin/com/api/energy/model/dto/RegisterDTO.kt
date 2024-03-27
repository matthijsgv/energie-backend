package com.api.energy.model.dto

import jakarta.validation.constraints.NotBlank


data class RegisterDTO(
        val username: String,
        @field:NotBlank(message = "Test")
        val password: String,
        val role: String,
        val name: String)

