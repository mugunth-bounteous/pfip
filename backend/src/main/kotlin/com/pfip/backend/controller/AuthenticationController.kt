package com.pfip.backend.controller


import com.pfip.backend.dto.CreateUserDto
import com.pfip.backend.dto.UserCredentials
import com.pfip.backend.service.AuthService
import com.pfip.backend.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["http://localhost:3000"])
class AuthenticationController(
    private val authService: AuthService
)
{
    @PostMapping("/login")
    fun login(@RequestBody req: UserCredentials): ResponseEntity<Any?> {
        return authService.login(req);
    }

    @PostMapping("/createUser")
    fun createUser(@RequestBody req: CreateUserDto): ResponseEntity<Any?>{
        return authService.createUser(req)
    }


}