package com.teamsparta.todosecurityproject.domain.feed.comment.repository

import com.teamsparta.todosecurityproject.domain.feed.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>, CustomCommentRepository {
    fun findAllByTodoCardIdOrderByCreatedAt(todoCardId: Long): List<Comment>
}