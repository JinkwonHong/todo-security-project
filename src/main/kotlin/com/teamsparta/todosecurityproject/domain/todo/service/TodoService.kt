package com.teamsparta.todosecurityproject.domain.todo.service

import com.teamsparta.todosecurityproject.domain.todo.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.todo.dto.*
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.common.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.common.exception.UnauthorizedException
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Pageable

@Service
class TodoService(
    val todoRepository: TodoRepository, val commentRepository: CommentRepository, val userRepository: UserRepository

) {
    fun findAllTodoCardsWithFilters(
        keyword: String?, category: Category?, completed: Boolean?, sort: String, pageable: Pageable
    ): Page<TodoCardResponseWithComments> {
        val todoCards = todoRepository.findAllWithFilters(keyword, category, completed, sort, pageable)
        return todoCards.map { TodoCardResponseWithComments.from(it) }
    }

    fun getTodoCardById(todoCardId: Long): TodoCardResponseWithComments {
        val todoCard = findTodoCardById(todoCardId)
        commentRepository.findAllByTodoCardIdOrderByCreatedAt(todoCardId)

        return TodoCardResponseWithComments.from(todoCard)
    }

    @Transactional
    fun createTodoCard(userId: Long, request: CreateTodoCardRequest): TodoCardResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val (title, description, category) = request
        val todoCard = TodoCard.of(title = title, description = description, category = category, user = user)

        return TodoCardResponse.from(todoRepository.save(todoCard))
    }

    @Transactional
    fun updateTodoCard(userId: Long, todoCardId: Long, request: UpdateTodoCardRequest): TodoCardResponse {
        val todoCard = findTodoCardById(todoCardId)
        checkUserAuthority(userId, todoCard)
        val (title, description, category, completed) = request
        todoCard.updateTodoCard(title = title, description = description, category = category, completed = completed)

        return TodoCardResponse.from(todoRepository.save(todoCard))
    }

    @Transactional
    fun deleteTodoCard(userId: Long, todoCardId: Long) {

        val todoCard = findTodoCardById(todoCardId)
        checkUserAuthority(userId, todoCard)

        todoRepository.delete(todoCard)
    }

    private fun findTodoCardById(todoCardId: Long): TodoCard {
        return todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("TodoCard", todoCardId)
    }

    private fun checkUserAuthority(userId: Long, todoCard: TodoCard) {
        if (todoCard.user.id != userId) {
            throw UnauthorizedException("You do not have access.")
        }
    }
}