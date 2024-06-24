package com.teamsparta.todosecurityproject.domain.todo.dto

import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.todo.comment.model.toResponse
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class TodoCardResponse(

    val title: String,
    val description: String,
    val user: UserResponse,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val comments: List<CommentResponse>
)

fun TodoCard.toResponse(): TodoCardResponse {
    return TodoCardResponse(
        title = title,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        user = user.toResponse(),
        comments = comments.map { it.toResponse() }
    )
}