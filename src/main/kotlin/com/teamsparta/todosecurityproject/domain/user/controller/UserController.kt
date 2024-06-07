package com.teamsparta.todosecurityproject.domain.user.controller

import com.teamsparta.todosecurityproject.domain.user.dto.SignRequest
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import com.teamsparta.todosecurityproject.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody signRequest: SignRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signRequest))
    }
}