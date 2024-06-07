package com.teamsparta.todosecurityproject.domain.user.model

import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "nickname")
    var nickname: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id ?: throw IllegalStateException("User ID cannot be null"),
            nickname = this.nickname,
        )
    }
}