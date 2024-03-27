package com.api.energy.controller

import com.api.energy.model.dto.LoginDTO
import com.api.energy.model.dto.RegisterDTO
import com.api.energy.service.UserService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (private val userService: UserService) {

    @PostMapping("/register")
    fun reqisterUser(@Valid @RequestBody registerDTO: RegisterDTO) {
        userService.registerUser(registerDTO)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) = userService.login(loginDTO)

    @GetMapping("/authorize")
    fun authorize(@RequestParam token: String) = userService.authorize(token)
}