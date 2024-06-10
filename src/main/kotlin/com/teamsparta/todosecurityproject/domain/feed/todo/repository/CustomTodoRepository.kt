package com.teamsparta.todosecurityproject.domain.feed.todo.repository

import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard

interface CustomTodoRepository {
    fun findAllWithComments(): List<TodoCard>
}