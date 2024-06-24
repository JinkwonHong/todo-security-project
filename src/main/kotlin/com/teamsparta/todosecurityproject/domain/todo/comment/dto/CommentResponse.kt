package com.teamsparta.todosecurityproject.domain.todo.comment.dto

import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.todo.dto.toResponse
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class CommentResponse(
    val content: String,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
    val user: UserResponse,
    val  todoCard: TodoCardResponse,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                comment.content,
                comment.createdAt,
                comment.updatedAt,
                comment.user.toResponse(),
                comment.todoCard.toResponse()
            )
        }
    }
}
