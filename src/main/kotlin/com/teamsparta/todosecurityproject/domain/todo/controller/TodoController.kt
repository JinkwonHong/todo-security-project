package com.teamsparta.todosecurityproject.domain.todo.controller

import com.teamsparta.todosecurityproject.domain.todo.service.TodoService
import com.teamsparta.todosecurityproject.domain.todo.dto.CreateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponseWithComments
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v2/todos")
@RestController
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping
    fun getAllTodoCards(
    ): ResponseEntity<List<TodoCardResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodoCards())
    }

    @GetMapping("/{todoCardId}")
    fun getTodoCardById(
        @PathVariable todoCardId: Long,
    ): ResponseEntity<TodoCardResponseWithComments> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoCardById(todoCardId))
    }

    @PostMapping
    fun createTodoCard(
        @Valid @RequestBody createTodoCardRequest: CreateTodoCardRequest
    ): ResponseEntity<TodoCardResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodoCard(createTodoCardRequest))
    }

    @PutMapping("/{todoCardId}")
    fun updateTodoCard(
        @PathVariable todoCardId: Long,
        @Valid @RequestBody updateTodoCardRequest: UpdateTodoCardRequest
    ): ResponseEntity<TodoCardResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.updateTodoCard(todoCardId, updateTodoCardRequest))
    }

    @DeleteMapping("/{todoCardId}")
    fun deleteTodoCard(
        @PathVariable todoCardId: Long
    ): ResponseEntity<Unit> {
        todoService.deleteTodoCard(todoCardId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}