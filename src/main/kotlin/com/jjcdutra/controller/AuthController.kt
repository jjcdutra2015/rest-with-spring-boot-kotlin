package com.jjcdutra.controller

import com.jjcdutra.data.vo.v1.AccountCredentialsVO
import com.jjcdutra.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping
    fun signin(@RequestBody data: AccountCredentialsVO): ResponseEntity<*> {
        return if (data.username.isNullOrBlank() || data.password.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request")
        else
            authService.signin(data)
    }
}