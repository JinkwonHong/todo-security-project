package com.teamsparta.todosecurityproject.domain.feed.comment.dto

import com.teamsparta.todosecurityproject.domain.feed.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.toResponse
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class CommentResponse(
    val content: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
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
