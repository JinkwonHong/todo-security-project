package com.teamsparta.todosecurityproject.domain.user.controller

import com.teamsparta.todosecurityproject.domain.user.dto.*
import com.teamsparta.todosecurityproject.domain.user.service.UserService
import com.teamsparta.todosecurityproject.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/login")
    fun signIn(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signIn(loginRequest))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }

    @PostMapping("/users/{userId}/profile")
    fun updateUserProfile(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable userId: Long,
        @RequestBody updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(principal.id, updateUserProfileRequest))
    }
}