package com.teamsparta.todosecurityproject.domain.feed.todo.controller

import com.teamsparta.todosecurityproject.domain.feed.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.feed.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v2/todos")
@RestController
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping("/{todoCardId}")
    fun getTodoCardById(
        @PathVariable todoCardId: Long,
    ): ResponseEntity<TodoCardResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoCardById(todoCardId))
    }

}