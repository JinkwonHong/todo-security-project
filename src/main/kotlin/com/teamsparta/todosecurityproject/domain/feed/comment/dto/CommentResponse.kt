package com.teamsparta.todosecurityproject.domain.feed.comment.dto

import com.teamsparta.todosecurityproject.domain.feed.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.user.model.User
import java.time.LocalDateTime

data class CommentResponse(
    val content: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val user: User,
    val  todoCard: TodoCardResponse,
)
