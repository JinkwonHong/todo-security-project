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

    fun updateProfile(newNickName: String) {
        this.nickname = newNickName
    }

    companion object {
        private fun checkAccountEmail(newAccountEmail: String) {
            if (!newAccountEmail.matches("^[a-z0-9]{4,10}@[a-z0-9]+(\\.[a-z]{2,6})$".toRegex())) {
                throw IllegalArgumentException("Invalid account EMAIL.")
            }
        }

        fun of(
            email: String,
            password: String,
            nickname: String,
        ): User {
            checkAccountEmail(email)
            return User(
                email = email, password = password, nickname = nickname
            )
        }
    }
}