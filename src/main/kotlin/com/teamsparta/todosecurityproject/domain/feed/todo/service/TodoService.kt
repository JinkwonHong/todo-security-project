package com.teamsparta.todosecurityproject.domain.feed.todo.service

import com.teamsparta.todosecurityproject.domain.feed.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.toResponse
import com.teamsparta.todosecurityproject.domain.feed.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,

    ) {
    fun getTodoCardById(todoCardId: Long): TodoCardResponse {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        val comments = commentRepository.findAllByTodoCardIdOrderByCreatedAt(todoCardId)

        return todoCard.toResponse(comments)
    }
}