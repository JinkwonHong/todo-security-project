package com.teamsparta.todosecurityproject.domain.user.dto

import com.teamsparta.todosecurityproject.domain.user.model.User

data class UserResponse(
    val id: Long, val nickname: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(id = user.id!!, nickname = user.nickname)
        }
    }
}