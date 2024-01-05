package com.jjcdutra.controller

import com.jjcdutra.data.vo.v1.AccountCredentialsVO
import com.jjcdutra.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Endpoint")
class AuthController(
    private val authService: AuthService
) {

    @Operation(summary = "Authenticate an user and return a token")
    @PostMapping(value = ["/signin"])
    fun signin(@RequestBody data: AccountCredentialsVO): ResponseEntity<*> {
        return if (data.username.isNullOrBlank() || data.password.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request")
        else
            authService.signin(data)
    }

    @Operation(summary = "Refresh token for authenticated user and return a token")
    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(
        @PathVariable("username") username: String?,
        @RequestHeader("Authorization") refreshToken: String?
    ): ResponseEntity<*> {
        return if (username.isNullOrBlank() || refreshToken.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request")
        else
            authService.refreshToken(username, refreshToken)
    }
}