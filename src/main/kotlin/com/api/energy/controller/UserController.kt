package com.api.energy.controller

import com.api.energy.model.dto.LoginDTO
import com.api.energy.model.dto.RegisterDTO
import com.api.energy.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (private val userService: UserService) {

    @PostMapping("/register")
    fun reqisterUser(@RequestBody registerDTO: RegisterDTO) {
        userService.registerUser(registerDTO)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) = userService.login(loginDTO)
}