package com.teamsparta.todosecurityproject.domain.user.service

import com.teamsparta.todosecurityproject.domain.user.dto.*
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtPlugin: JwtPlugin
) {
    @Transactional
    fun signIn(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(loginRequest.email) ?: throw ModelNotFoundException("User", null)

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email
            )
        )
    }

    @Transactional
    fun signUp(signUpRequest: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(signUpRequest.email)) throw IllegalStateException("Email is already in use")

        val user = User(
            email = signUpRequest.email,
            password = passwordEncoder.encode(signUpRequest.password),
            nickname = signUpRequest.nickname
        )
        return userRepository.save(user).toResponse()
    }

    @Transactional
    fun updateUserProfile(userId: Long, updateUserProfileRequest: UpdateUserProfileRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.nickname = updateUserProfileRequest.nickname

        return user.toResponse()
    }
}
