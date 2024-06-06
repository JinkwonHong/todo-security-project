package com.teamsparta.todosecurityproject.domain.feed.comment.model

import com.teamsparta.todosecurityproject.domain.feed.basetime.model.BaseTime
import com.teamsparta.todosecurityproject.domain.feed.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content")
    var content: String,

    @ManyToOne
    var user: User,

    @ManyToOne @JoinColumn(name = "todo_id")
    val todoCard: TodoCard

) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}