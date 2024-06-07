package com.teamsparta.todosecurityproject.domain.feed.todo.service

import com.teamsparta.todosecurityproject.domain.feed.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.*
import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.feed.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,

    ) {
    fun getAllTodoCards() : List<TodoCardResponse> {
        return todoRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
    }

    fun getTodoCardById(todoCardId: Long): TodoCardResponseWithComments {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        val comments = commentRepository.findAllByTodoCardIdOrderByCreatedAt(todoCardId)

        return todoCard.toResponseWithComments(comments)
    }

    @Transactional
    fun createTodoCard(request: CreateTodoCardRequest): TodoCardResponse {
        val todoCard = TodoCard(
            title = request.title,
            description = request.description,
            user = request.user
        )

        return todoRepository.save(todoCard).toResponse()
    }

    @Transactional
    fun updateTodoCard(todoCardId: Long, updateTodoCardRequest: UpdateTodoCardRequest): TodoCardResponse {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        todoCard.updateTodoCardField(updateTodoCardRequest)

        if (todoCard.user.id != updateTodoCardRequest.user.id)
            throw UnauthorizedException("Cannot modify TodoCard because you do not have user rights.")

        return todoCard.toResponse()
    }

    @Transactional
    fun deleteTodoCard(todoCardId: Long) {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)

        todoRepository.delete(todoCard)
    }
}