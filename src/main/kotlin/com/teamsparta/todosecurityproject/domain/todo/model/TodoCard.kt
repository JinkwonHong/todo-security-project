package com.teamsparta.todosecurityproject.domain.todo.model

import com.teamsparta.todosecurityproject.common.time.model.BaseTime
import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.user.model.User
import jakarta.persistence.*
import java.io.InvalidObjectException

@Entity
@Table(name = "todo_card")
class TodoCard(
    @Column(name = "title") var title: String,

    @Column(name = "description") var description: String,

    @ManyToOne val user: User,

    @OneToMany val comments: MutableList<Comment> = mutableListOf(),

    @Column(name = "completed") var completed: Boolean = false,

    @Enumerated(EnumType.STRING) @Column(name = "category") var category: Category,
) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        private fun checkTitleLength(newTitle: String) {
            if (newTitle.isEmpty() || newTitle.length > 10) {
                throw InvalidObjectException("Please write your Title with a minimum of 1 character and a maximum of 10 characters.")
            }
        }

        private fun checkDescriptionLength(newDescription: String) {
            if (newDescription.isEmpty() || newDescription.length > 200) {
                throw InvalidObjectException("Please write your Description with a minimum of 1 character and a maximum of 200 characters.")
            }
        }

        fun of(title: String, description: String, user: User, category: String): TodoCard {
            checkTitleLength(title)
            checkDescriptionLength(description)
            val todoCard = TodoCard(
                title = title, description = description, user = user, category = Category.fromString(category)
            )
            todoCard.setCreatedAt()
            return todoCard
        }
    }

    fun updateTodoCard(title: String, description: String, category: String, completed: Boolean) {
        checkTitleLength(title)
        checkDescriptionLength(description)
        this.title = title
        this.description = description
        this.category = Category.fromString(category)
        this.completed = completed
        this.setUpdatedAt()
    }
}
