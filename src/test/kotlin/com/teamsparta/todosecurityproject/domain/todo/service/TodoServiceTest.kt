package com.teamsparta.todosecurityproject.domain.todo.service

import com.teamsparta.todosecurityproject.common.exception.UnauthorizedException
import com.teamsparta.todosecurityproject.domain.todo.comment.repository.CommentRepository
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.infra.querydsl.QueryDslConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])
@ActiveProfiles("test")
class TodoServiceTest @Autowired constructor (private val todoRepository: TodoRepository, private val commentRepository: CommentRepository, private val userRepository: UserRepository){
    private val todoService = TodoService(todoRepository, commentRepository, userRepository)

    @Test
    fun `TodoCard 를 생성한 유저가 아닌, 다른 유저가 TodoCard 를 수정하려고 시도할 경우 예외처리 확인`() {
        // GIVEN
        userRepository.saveAllAndFlush(DEFAULT_USER_LIST)
        todoRepository.saveAllAndFlush(DEFAULT_TODOCARD_LIST)

        val updateRequest = UpdateTodoCardRequest(title = "Updated Title", description = "Updated Description", category = "EXERCISE", completed = true)

        // WHEN & THEN
        shouldThrow<UnauthorizedException> {
            todoService.updateTodoCard(DEFAULT_USER_LIST[1].id!!, DEFAULT_TODOCARD_LIST[0].id!!, updateRequest)
        }.let { it.message shouldBe "You do not have access." }

        val unchangedTodoCard = todoRepository.findById(DEFAULT_TODOCARD_LIST[0].id!!)
        unchangedTodoCard.get().title shouldNotBe "Updated Title"
    }

    @Test
    fun `정상적으로 회원가입되는 시나리오 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    companion object {
        private val DEFAULT_USER_LIST = listOf(
            User(email = "user1@naver.com", password = "password1", nickname = "user1"),
            User(email = "user2@gmail.com", password = "password2", nickname = "user2")
        )

        private val DEFAULT_TODOCARD_LIST = listOf(
            TodoCard(title = "Test Title", description = "Test Description", category = Category.STUDY, user = DEFAULT_USER_LIST[0], comments = mutableListOf(), completed = false)
        )
    }
}