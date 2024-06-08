package com.teamsparta.todosecurityproject.domain.user.repository

import com.teamsparta.todosecurityproject.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}