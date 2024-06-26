package com.teamsparta.todosecurityproject.domain.todo.dto

data class UpdateTodoCardRequest(
    val title: String,
    val description: String,
    val category: String,
    val completed: Boolean
)