package com.teamsparta.todosecurityproject.domain.todo.controller

import com.ninjasquad.springmockk.MockkBean
import com.teamsparta.todosecurityproject.common.exception.UnauthorizedException
import com.teamsparta.todosecurityproject.domain.todo.dto.TodoCardResponse
import com.teamsparta.todosecurityproject.domain.todo.dto.UpdateTodoCardRequest
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.service.TodoService
import com.teamsparta.todosecurityproject.domain.user.dto.UserResponse
import com.teamsparta.todosecurityproject.infra.security.jwt.JwtPlugin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class TodoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,

    @MockkBean
    private val todoService: TodoService
) : DescribeSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    describe("PUT /todo/{todoCardId}는") {
        context("정상적으로 Update TodoCard 를 시도할 때") {
            it("200 status code 를 응답해야한다.") {
                val todoCardId = 1L
                val updateRequest =
                    """{"title":"update","description":"Updated Description","category":"EXERCISE","completed":"true"}"""
                val userResponse = UserResponse(1L, "TestUser")
                val todoCardResponse = TodoCardResponse("update","Updated Description", userResponse, Category.EXERCISE, true, null, null)

                every { todoService.updateTodoCard(any<Long>(), any<Long>(), any<UpdateTodoCardRequest>())} returns todoCardResponse

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "test@naver.com"
                )

                val result = mockMvc.perform(
                    put("/api/v2/todos/$todoCardId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest)
                ).andReturn()

                result.response.status shouldBe 200
            }
        }

        context("작성자가 아닌 TodoCard 의 Update 를 시도할 때") {
            it("401 status code 를 응답해야한다.") {
                val todoCardId = 1L
                val updateRequest =
                    """{"title":"update","description":"Updated Description","category":"EXERCISE","completed":"true"}"""

                every { todoService.updateTodoCard(any<Long>(), any<Long>(), any<UpdateTodoCardRequest>())} throws UnauthorizedException("You do not have access.")

                val jwtToken = jwtPlugin.generateAccessToken(
                    subject = "1",
                    email = "test@naver.com"
                )

                val result = mockMvc.perform(
                    put("/api/v2/todos/$todoCardId")
                        .header("Authorization", "Bearer $jwtToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest)
                ).andReturn()

                result.response.status shouldBe 401
            }
        }
    }
})