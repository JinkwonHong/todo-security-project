package com.teamsparta.todosecurityproject.domain.todo.comment.repository

import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByTodoCardIdOrderByCreatedAt(todoCardId: Long): List<Comment>
}