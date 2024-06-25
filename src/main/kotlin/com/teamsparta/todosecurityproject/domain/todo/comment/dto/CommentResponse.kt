package com.teamsparta.todosecurityproject.domain.todo.comment.dto

import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import java.time.ZonedDateTime

data class CommentResponse(
    val content: String,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
    val user: UserResponse,
    val todoCard: TodoCardResponse,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
                user = comment.user.toResponse(),
                todoCard = TodoCardResponse.from(comment.todoCard)
            )
        }
    }
}
