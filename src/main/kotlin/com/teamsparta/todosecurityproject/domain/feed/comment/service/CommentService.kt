package com.teamsparta.todosecurityproject.domain.feed.comment.service

import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CreateCommentRequest
import com.teamsparta.todosecurityproject.domain.feed.comment.dto.UpdateCommentRequest
import com.teamsparta.todosecurityproject.domain.feed.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.feed.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.feed.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.exception.ModelNotFoundException
import com.teamsparta.todosecurityproject.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    val commentRepository: CommentRepository,
    val todoRepository: TodoRepository,
    val userRepository: UserRepository
) {
    @Transactional
    fun createComment(todoCardId: Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val todoCard = todoRepository.findByIdOrNull(todoCardId) ?: throw ModelNotFoundException("todoCard", todoCardId)
        val user =
            userRepository.findByIdOrNull(createCommentRequest.userId) ?: throw ModelNotFoundException(
                "User",
                createCommentRequest.userId
            )
        val comment = Comment(
            content = createCommentRequest.content,
            user = user,
            todoCard = todoCard

        )
        return CommentResponse.from(commentRepository.save(comment))
    }

    @Transactional
     fun updateComment(
        postId: Long,
        commentId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse {
        todoRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != updateCommentRequest.userId )
            throw UnauthorizedException("You do not have permission to modify.")

        comment.content = updateCommentRequest.content

        return CommentResponse.from(comment)
    }
}