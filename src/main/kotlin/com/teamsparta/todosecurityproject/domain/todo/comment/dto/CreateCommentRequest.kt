package com.teamsparta.todosecurityproject.domain.todo.comment.dto

import jakarta.validation.constraints.Size

data class CreateCommentRequest(
@field: Size(min = 1, max = 100, message = "Please enter at least 1 character and less than 100 characters")
val content: String,
)
