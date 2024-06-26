package com.teamsparta.todosecurityproject.domain.todo.controller

import com.teamsparta.todosecurityproject.domain.todo.service.TodoService
import com.teamsparta.todosecurityproject.domain.todo.dto.CreateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponseWithComments
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault

@RequestMapping("/api/v2/todos")
@RestController
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping("/search")
    fun findAllTodoCardsWithFilters(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) category: Category?,
        @RequestParam(required = false) completed: Boolean?,
        @RequestParam(required = false, defaultValue = "createdAt") sort: String,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<TodoCardResponseWithComments>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(todoService.findAllTodoCardsWithFilters(keyword, category, completed, sort, pageable))
    }

    @GetMapping("/{todoCardId}")
    fun getTodoCardById(
        @PathVariable todoCardId: Long,
    ): ResponseEntity<TodoCardResponseWithComments> {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodoCardById(todoCardId))
    }

    @PostMapping
    fun createTodoCard(
        @AuthenticationPrincipal principal: UserPrincipal, @RequestBody createTodoCardRequest: CreateTodoCardRequest
    ): ResponseEntity<TodoCardResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoService.createTodoCard(principal.id, createTodoCardRequest))
    }

    @PutMapping("/{todoCardId}")
    fun updateTodoCard(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable todoCardId: Long,
        @RequestBody updateTodoCardRequest: UpdateTodoCardRequest
    ): ResponseEntity<TodoCardResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoService.updateTodoCard(principal.id, todoCardId, updateTodoCardRequest))
    }

    @DeleteMapping("/{todoCardId}")
    fun deleteTodoCard(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable todoCardId: Long
    ): ResponseEntity<Unit> {
        todoService.deleteTodoCard(principal.id, todoCardId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}