package com.teamsparta.todosecurityproject.domain.feed.todo.dto

import com.teamsparta.todosecurityproject.domain.feed.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import com.teamsparta.todosecurityproject.domain.user.model.User
import java.time.LocalDateTime

data class TodoCardResponseWithComments(

    val title: String,
    val description: String,
    val user: UserResponse,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val comments: List<Comment>
)

fun TodoCard.toResponseWithComments(comments: List<Comment>): TodoCardResponseWithComments {
    return TodoCardResponseWithComments(
        title = title,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        user = user.toResponse(),
        comments = comments
    )
}
