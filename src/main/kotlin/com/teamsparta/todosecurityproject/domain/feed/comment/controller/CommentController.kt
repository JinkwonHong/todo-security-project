package com.teamsparta.todosecurityproject.domain.feed.comment.controller

import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.feed.comment.dto.CreateCommentRequest
import com.teamsparta.todosecurityproject.domain.feed.comment.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v2/todos/{todoCardId}/comments")
@RestController
class CommentController(
    val commentService: CommentService
) {
    @PostMapping
    fun createComment(
        @PathVariable todoCardId: Long, @Valid @RequestBody createCommentRequest: CreateCommentRequest): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(todoCardId, createCommentRequest))
    }

}