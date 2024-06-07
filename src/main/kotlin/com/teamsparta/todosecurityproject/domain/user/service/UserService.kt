package com.teamsparta.todosecurityproject.domain.user.service

import com.teamsparta.todosecurityproject.domain.user.dto.SignUpRequest
import com.teamsparta.todosecurityproject.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository
) {
    @Transactional
    fun signUp(signUpRequest: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(signUpRequest.email)) throw IllegalStateException("Email is already in use")

        val user = User(
            email = signUpRequest.email,
            password = signUpRequest.password,
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
