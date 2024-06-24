package com.teamsparta.todosecurityproject.domain.todo.repository

import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard

interface CustomTodoRepository {
    fun findAllWithComments(): List<TodoCard>
}