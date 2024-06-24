package com.teamsparta.todosecurityproject.domain.todo.comment.controller

import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CommentResponse
import com.teamsparta.todosecurityproject.domain.todo.comment.dto.CreateCommentRequest
import com.teamsparta.todosecurityproject.domain.todo.comment.dto.UpdateCommentRequest
import com.teamsparta.todosecurityproject.domain.todo.comment.service.CommentService
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
        @PathVariable todoCardId: Long, @Valid @RequestBody createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(todoCardId, createCommentRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(@PathVariable todoCardId: Long, @PathVariable commentId: Long,
                      @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(todoCardId, commentId, updateCommentRequest))

    }
}