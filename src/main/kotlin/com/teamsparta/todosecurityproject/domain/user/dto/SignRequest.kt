package com.teamsparta.todosecurityproject.domain.user.dto

data class SignRequest(
    val email: String,

    val password: String,

    val nickname: String
)