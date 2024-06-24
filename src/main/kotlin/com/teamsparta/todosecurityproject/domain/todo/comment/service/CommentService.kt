package com.teamsparta.todosecurityproject.domain.todo.comment.service

import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CreateCommentRequest
import com.teamsparta.todosecurityproject.domain.todo.comment.dto.UpdateCommentRequest
import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.todo.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.common.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.common.exception.UnauthorizedException
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val todoRepository: TodoRepository,
    val userRepository: UserRepository
) {
    @Transactional
    fun createComment(userId: Long, todoCardId: Long, request: CreateCommentRequest): CommentResponse {
        val todoCard = findTodoCardById(todoCardId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val (content) = request

        val comment = Comment.of(content = content, user = user, todoCard = todoCard)
        return CommentResponse.from(commentRepository.save(comment))
    }

    @Transactional
    fun updateComment(
        userId: Long,
        todoCardId: Long,
        commentId: Long,
        request: UpdateCommentRequest
    ): CommentResponse {
        findTodoCardById(todoCardId)

        val comment = findCommentById(commentId)
        checkUserAuthority(userId, comment)

        val (content) = request
        comment.updateComment(content = content)

        return CommentResponse.from(comment)
    }

    private fun findTodoCardById(todoCardId: Long): TodoCard {
        return todoRepository.findByIdOrNull(todoCardId)
            ?: throw ModelNotFoundException("Comment", todoCardId)
    }

    private fun findCommentById(commentId: Long): Comment {
        return commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
    }

    private fun checkUserAuthority(userId: Long, comment: Comment) {
        if (comment.user.id != userId) {
            throw UnauthorizedException("You do not have permission to modify.")
        }
    }
}