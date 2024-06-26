package com.teamsparta.todosecurityproject.domain.todo.repository

import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomTodoRepository {
    fun findAllWithFilters(
        keyword: String?, category: Category?, completed: Boolean?, sort: String, pageable: Pageable
    ): Page<TodoCard>
}
