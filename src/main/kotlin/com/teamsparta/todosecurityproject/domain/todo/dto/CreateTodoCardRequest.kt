package com.teamsparta.todosecurityproject.domain.todo.dto

import jakarta.validation.constraints.Size

data class CreateTodoCardRequest(
    @field: Size(min = 1, max = 100, message = "Please enter at least 1 character and less than 100 characters")
    val title: String,

    @field: Size(min = 1, max = 1000, message = "Please enter at least 1 character and less than 1000 characters")
    val description: String,

    val userId: Long,
)