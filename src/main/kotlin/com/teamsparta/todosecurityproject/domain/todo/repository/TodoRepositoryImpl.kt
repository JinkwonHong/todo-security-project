package com.teamsparta.todosecurityproject.domain.todo.repository

import com.teamsparta.todosecurityproject.domain.todo.comment.model.QComment
import com.teamsparta.todosecurityproject.domain.todo.model.QTodoCard
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl: CustomTodoRepository, QueryDslSupport() {
    override fun findAllWithComments(): List<TodoCard> {
        val todoCard = QTodoCard.todoCard
        val comment = QComment.comment

        return queryFactory
            .selectFrom(todoCard)
            .leftJoin(todoCard.comments, comment)
            .fetchJoin()
            .fetch()
    }
}