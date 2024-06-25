package com.teamsparta.todosecurityproject.domain.todo.dto

import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import java.time.ZonedDateTime

data class TodoCardResponse(
    val title: String,
    val description: String,
    val user: UserResponse,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
) {
    companion object {
        fun from(todoCard: TodoCard): TodoCardResponse {
            return TodoCardResponse(
                title = todoCard.title,
                description = todoCard.description,
                user = todoCard.user.toResponse(),
                createdAt = todoCard.createdAt,
                updatedAt = todoCard.updatedAt,
            )
        }
    }
}