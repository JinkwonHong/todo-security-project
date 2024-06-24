package com.teamsparta.todosecurityproject.domain.todo.repository

import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<TodoCard, Long>, CustomTodoRepository {
    fun findAllByOrderByCreatedAtDesc(): List<TodoCard>
}