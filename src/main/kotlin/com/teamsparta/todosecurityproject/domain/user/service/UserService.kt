package com.teamsparta.todosecurityproject.domain.user.service

import com.teamsparta.todosecurityproject.domain.user.dto.*
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.common.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository, val passwordEncoder: PasswordEncoder, val jwtPlugin: JwtPlugin
) {
    fun signIn(request: LoginRequest): LoginResponse {
        val (email, password) = request
        val user = userRepository.findByEmail(email)?.takeIf { passwordEncoder.matches(password, it.password) }
            ?: throw IllegalArgumentException("Please check your email and password")

        return LoginResponse(accessToken = jwtPlugin.generateAccessToken(user.id.toString(), user.email))
    }

    @Transactional
    fun signUp(request: SignUpRequest): UserResponse {

        val (email, password, nickname) = request
        if (userRepository.existsByEmail(email)) throw IllegalArgumentException("Email: '${email}' is already in use")
        checkPasswordRule(password)
        val encodedPassword = passwordEncoder.encode(password)

        val user = User.of(
            email = email, password = encodedPassword, nickname = nickname
        )
        return UserResponse.from(userRepository.save(user))
    }

    @Transactional
    fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse {
        val (password, nickname) = request
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Please check your password.")
        }
        user.updateProfile(nickname)

        return UserResponse.from(user)
    }

    private fun checkPasswordRule(password: String) {
        if (!password.matches("^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,15}\$".toRegex())) {
            throw IllegalArgumentException("Invalid Password.")
        }
    }
}