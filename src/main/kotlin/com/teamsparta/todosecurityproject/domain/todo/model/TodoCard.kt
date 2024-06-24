package com.teamsparta.todosecurityproject.domain.todo.model

import com.teamsparta.todosecurityproject.common.model.BasedTime
import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "todo_card")
class TodoCard(
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @ManyToOne
    val user: User,

    @OneToMany
    val comments: MutableList<Comment> = mutableListOf()
) : BasedTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateTodoCardField(updateTodoCardRequest: UpdateTodoCardRequest) {
        title = updateTodoCardRequest.title
        description = updateTodoCardRequest.description
    }
}