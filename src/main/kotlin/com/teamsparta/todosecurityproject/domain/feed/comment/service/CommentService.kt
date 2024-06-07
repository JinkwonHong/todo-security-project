package com.teamsparta.todosecurityproject.domain.feed.comment.service

import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CreateCommentRequest
import com.teamsparta.todosecurityproject.domain.feed.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.feed.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val todoRepository: TodoRepository,
    val userRepository: UserRepository
) {
    fun createComment(todoCardId: Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
}