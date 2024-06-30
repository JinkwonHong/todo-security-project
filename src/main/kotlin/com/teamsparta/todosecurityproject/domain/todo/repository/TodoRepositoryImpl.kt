package com.teamsparta.todosecurityproject.domain.todo.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.OrderSpecifier
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.model.QTodoCard
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryImpl : CustomTodoRepository, QueryDslSupport() {
    override fun findAllWithFilters(
        keyword: String?, category: Category?, completed: Boolean?, sort: String, pageable: Pageable
    ): Page<TodoCard> {
        val todoCard = QTodoCard.todoCard

        val whereClause = BooleanBuilder()
        keyword?.let {
            whereClause.and(
                todoCard.title.containsIgnoreCase(it).or(todoCard.description.containsIgnoreCase(it))
            )
        }
        category?.let { whereClause.and(todoCard.category.eq(it)) }
        completed?.let { whereClause.and(todoCard.completed.eq(it)) }

        val totalCount = queryFactory.select(todoCard.count()).from(todoCard).where(whereClause).fetchOne() ?: 0L

        val orderSpecifier = when (sort) {
            "title" -> todoCard.title.asc()
            "nickname" -> todoCard.user.nickname.asc()
            else -> todoCard.createdAt.desc()
        }

        val query = queryFactory.selectFrom(todoCard).leftJoin(todoCard.user).fetchJoin().leftJoin(todoCard.comments)
            .fetchJoin().where(whereClause).offset(pageable.offset).limit(pageable.pageSize.toLong())
            .orderBy(orderSpecifier).distinct().fetch()

        return PageImpl(query, pageable, totalCount)
    }
}
