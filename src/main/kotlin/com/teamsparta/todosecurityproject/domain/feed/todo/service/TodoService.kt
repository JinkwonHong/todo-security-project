package com.teamsparta.todosecurityproject.domain.feed.todo.service

import com.teamsparta.todosecurityproject.domain.feed.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.*
import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.feed.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    val todoRepository: TodoRepository,
    val commentRepository: CommentRepository,
    val userRepository: UserRepository

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
    fun createTodoCard(createTodoCardRequest: CreateTodoCardRequest): TodoCardResponse {
        val user =
            userRepository.findByIdOrNull(createTodoCardRequest.userId) ?: throw ModelNotFoundException(
                "User",
                createTodoCardRequest.userId
            )

        val todoCard = TodoCard(
            title = createTodoCardRequest.title,
            description = createTodoCardRequest.description,
            user = user
        )

        return todoRepository.save(todoCard).toResponse()
    }

    @Transactional
    fun updateTodoCard(todoCardId: Long, updateTodoCardRequest: UpdateTodoCardRequest): TodoCardResponse {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        todoCard.updateTodoCardField(updateTodoCardRequest)

        if (todoCard.user.id != updateTodoCardRequest.userId)
            throw UnauthorizedException("You do not have permission to modify.")

        return todoCard.toResponse()
    }

    @Transactional
    fun deleteTodoCard(todoCardId: Long) {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)

        todoRepository.delete(todoCard)
    }
}