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
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val (content) = request

        val comment = Comment.of(content = content, user = user, todoCard = todoCard)
        return CommentResponse.from(commentRepository.save(comment))
    }

    @Transactional
    fun updateComment(
        postId: Long,
        commentId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse {

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        todoRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != userPrincipal.id)
            throw UnauthorizedException("You do not have permission to modify.")

        comment.content = updateCommentRequest.content

        return CommentResponse.from(comment)
    }
}