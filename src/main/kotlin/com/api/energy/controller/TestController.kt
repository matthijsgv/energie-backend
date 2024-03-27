package com.api.energy.controller

import com.api.energy.service.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController (private val tokenService: TokenService) {

    @GetMapping
    fun testTokenParser(@RequestParam token: String) =
        tokenService.parseToken(token)

}