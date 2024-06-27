package com.teamsparta.todosecurityproject.domain.todo.repository

import com.teamsparta.todosecurityproject.domain.todo.comment.model.Comment
import com.teamsparta.todosecurityproject.domain.todo.model.Category
import com.teamsparta.todosecurityproject.domain.todo.model.TodoCard
import com.teamsparta.todosecurityproject.domain.user.model.User
import com.teamsparta.todosecurityproject.infra.querydsl.QueryDslSupport
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
@ActiveProfiles("test")
class TodoRepositoryTest @Autowired constructor(
private val todosRepository: TodoRepository
) {
    @Test
    fun `Search, Category, Completed 필터 모두 입력하지 않았을 때, Created Desc 으로 정렬되어 조회되는지`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `입력한 Keyword 가 Title 또는 Description 에 포함되어 있을 경우 해당 TodoCard 모두 조회되는지 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `Category 설정 시, 해당 Category 에 해당하는 TodoCard 모두 조회되는지 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `완료여부 False 를 조회할 경우, False 에 해당하는 TodoCard 모두 조회되는지 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `조회된 결과가 15개, 0Page 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `조회된 결과가 15개, 1Page 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }
    companion object {
        private val DEFAULT_USER1 = User(email = "user1@naver.com", password = "password1", nickname = "user1")
        private val DEFAULT_USER2 = User(email = "user2@gmail.com", password = "password2", nickname = "user2")

        private val DEFAULT_TODOCARD_LIST = listOf(
            TodoCard(title = "06/24 오늘의 헬스 진행 계획", description = "벤치프레스 5세트, 덤벨프레스 5세트", user = DEFAULT_USER1, comments = mutableListOf(), completed = false, category = Category.EXERCISE),
            TodoCard(title = "06/24 공부 계획", description = "알고리즘 문제 풀기, 데이터베이스 복습", user = DEFAULT_USER2, comments = mutableListOf(), completed = false, category = Category.STUDY),
            TodoCard(title = "06/24 직장 업무", description = "주간 보고서 작성, 회의 준비", user = DEFAULT_USER1, comments = mutableListOf(), completed = true, category = Category.WORK),
            TodoCard(title = "06/24 친구와의 약속", description = "저녁 식사, 영화 보기", user = DEFAULT_USER2, comments = mutableListOf(), completed = false, category = Category.PROMISE),
            TodoCard(title = "06/25 기타 할 일", description = "은행 업무, 쇼핑", user = DEFAULT_USER1, comments = mutableListOf(), completed = true, category = Category.OTHER),
            TodoCard(title = "06/26 운동 계획", description = "스쿼트 5세트, 데드리프트 5세트", user = DEFAULT_USER2, comments = mutableListOf(), completed = false, category = Category.EXERCISE),
            TodoCard(title = "06/26 공부 계획", description = "자바 프로그래밍 복습, 코딩 테스트 준비", user = DEFAULT_USER1, comments = mutableListOf(), completed = false, category = Category.STUDY),
            TodoCard(title = "07/01 업무 계획", description = "프로젝트 미팅, 코드 리뷰", user = DEFAULT_USER2, comments = mutableListOf(), completed = true, category = Category.WORK),
            TodoCard(title = "07/01 약속", description = "오랜만의 친구와 저녁", user = DEFAULT_USER1, comments = mutableListOf(), completed = false, category = Category.PROMISE),
            TodoCard(title = "07/01 기타", description = "집 청소, 정리 정돈", user = DEFAULT_USER2, comments = mutableListOf(), completed = true, category = Category.OTHER),
            TodoCard(title = "07/02 운동", description = "유산소 운동, 스트레칭", user = DEFAULT_USER1, comments = mutableListOf(), completed = false, category = Category.EXERCISE),
            TodoCard(title = "07/02 학습", description = "데이터 사이언스 공부, 통계 복습", user = DEFAULT_USER2, comments = mutableListOf(), completed = false, category = Category.STUDY),
            TodoCard(title = "07/03 직장 업무", description = "클라이언트 회의, 코드 배포", user = DEFAULT_USER1, comments = mutableListOf(), completed = true, category = Category.WORK),
            TodoCard(title = "07/03 친구와의 약속", description = "카페에서 대화, 산책", user = DEFAULT_USER2, comments = mutableListOf(), completed = false, category = Category.PROMISE),
            TodoCard(title = "07/07 기타 할 일", description = "서류 정리, 물건 정리", user = DEFAULT_USER1, comments = mutableListOf(), completed = true, category = Category.OTHER)
        ).apply {
            val defaultComment1 = Comment(content = "Comment1 content", user = DEFAULT_USER2, todoCard = this[0])
            val defaultComment2 = Comment(content = "Comment2 content", user = DEFAULT_USER1, todoCard = this[1])
            this[0].comments.add(defaultComment1)
            this[1].comments.add(defaultComment2)
        }
    }
}