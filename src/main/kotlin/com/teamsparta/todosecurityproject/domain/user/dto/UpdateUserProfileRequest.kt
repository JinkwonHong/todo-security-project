package com.teamsparta.todosecurityproject.domain.user.dto

data class UpdateUserProfileRequest(
    val password: String,
    val nickname: String
)
