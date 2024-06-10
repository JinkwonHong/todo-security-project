package com.teamsparta.todosecurityproject.domain.feed.todo.model

import com.teamsparta.todosecurityproject.domain.feed.basetime.model.BaseTime
import com.teamsparta.todosecurityproject.domain.feed.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.feed.todo.dto.UpdateTodoCardRequest
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
) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateTodoCardField(updateTodoCardRequest: UpdateTodoCardRequest) {
        title = updateTodoCardRequest.title
        description = updateTodoCardRequest.description
    }
}