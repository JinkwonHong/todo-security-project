package com.teamsparta.todosecurityproject.domain.todo.dto

data class CreateTodoCardRequest(
    val title: String,
    val description: String,
    val category: String
)