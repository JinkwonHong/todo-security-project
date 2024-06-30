package com.teamsparta.todosecurityproject.domain.todo.controller

import com.ninjasquad.springmockk.MockkBean
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.todo.repository.TodoRepository
import com.teamsparta.todosecurityproject.domain.todo.service.TodoService
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.domain.user.repository.UserRepository
import com.teamsparta.todosecurityproject.domain.user.service.UserService
import com.teamsparta.todosecurityproject.infra.security.jwt.JwtPlugin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,
    @MockkBean
    private val todoService: TodoService,
    @MockkBean
    private val userService: UserService,
    @MockkBean
    private val userRepository: UserRepository,
    @MockkBean
    private val todoRepository: TodoRepository
) : DescribeSpec({
    extension(SpringExtension)

    describe("PUT /todo/{todoCardId}는") {
        context("정상적으로 Update TodoCard 를 시도할 때") {
            it("200 status code 를 응답해야한다.") {
                val user =
                    userRepository.save(User(email = "test@naver.com", password = "password123", nickname = "testUser"))
                val todoCard = todoRepository.save(
                    TodoCard(title = "TestTitle", description = "TestDescription", category = Category.STUDY, user = user, completed = false)
                )
                val updateRequest = UpdateTodoCardRequest(title = "Update", description = "Updated Description", category = "EXERCISE", completed = true)
                val updatedTodoCard = todoCard.apply {
                    title = "Update"
                    description = "Updated Description"
                    category = Category.EXERCISE
                    completed = true
                }
                val response = TodoCardResponse.from(updatedTodoCard)
                every { todoService.updateTodoCard(user.id!!, todoCard.id!!, updateRequest) } returns response

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "test@naver.com"
                )

                val result = mockMvc.perform(
                    put("/api/v2/todos/${todoCard.id}")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200
            }
        }
    }
})