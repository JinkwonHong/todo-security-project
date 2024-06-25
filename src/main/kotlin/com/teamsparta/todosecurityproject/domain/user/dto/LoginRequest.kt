package com.teamsparta.todosecurityproject.domain.user.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
