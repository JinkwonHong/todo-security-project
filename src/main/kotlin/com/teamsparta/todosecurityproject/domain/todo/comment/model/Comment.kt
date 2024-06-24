package com.teamsparta.todosecurityproject.domain.todo.comment.model

import com.teamsparta.todosecurityproject.common.time.model.BasedTime
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.model.User
import jakarta.persistence.*
import java.io.InvalidObjectException

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "content") var content: String,

    @ManyToOne val user: User,

    @ManyToOne @JoinColumn(name = "todo_id") val todoCard: TodoCard

) : BasedTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        private fun checkContentLength(newContent: String) {
            if (newContent.isEmpty() || newContent.length > 100) {
                throw InvalidObjectException("Please write your Comment with a minimum of 1 character and a maximum of 100 characters.")
            }
        }

        fun of(content: String, user: User, todoCard: TodoCard): Comment {
            checkContentLength(content)
            val comment = Comment(
                content = content,
                user = user,
                todoCard = todoCard,
            )
            comment.setCreatedAt()
            return comment
        }
    }
    fun updateComment(content: String) {
        checkContentLength(content)
        this.content = content
        this.setUpdatedAt()
    }
}