package com.teamsparta.todosecurityproject.domain.feed.todo.repository

import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<TodoCard, Long> {
}