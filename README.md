# 🚀 TodoApp Security Project (TodoApp Version 2) + Spring Plus 코드 개선 과제

이번 프로젝트 미션은 이전 “투두앱 백엔드 서버 만들기” 프로젝트에 이어, “JWT 를 활용한 Security 구현”입니다.
과제는 Refactoring, 테이블 간 관계 매핑, ERD 의 변화 등을 이유로 새로운 Repository 를 만들어 새롭게 작업을 진행하였으며 ‘Version 1’ 의 하단의 Url 참고 부탁드리겠습니다.
- https://github.com/JinkownHong/todo-project.git

지난 과제 관련 피드백과 과제의 요구사항을 반영하여 주특기 플러스 과제를 진행하였습니다.

<img src="https://techcourse-storage.s3.ap-northeast-2.amazonaws.com/2020-03-16T10:41:53.786image.png" width="400">

# ✨ 주요 기능
각 STEP 별 소개 및 현재 어디까지 해당 기능을 구현했는지도 함께 표기하였습니다.
해당 내용은 기능을 추가할 때마다 지속적으로 변경될 내용인 점 참고 부탁드리겠습니다.
### STEP 1

* [x] **할일카드 작성 기능**
    * 할 일 제목, 할일 내용, 작성일, 작성자 이름을 저장 가능
    * 저장된 할 일의 정보를 반환 받아 확인 가능
* [x] **선택한 할 일 조회 기능**
    * 선택한 할 일의 정보 조회 가능
    * 반환된 할 일 정보에는 할 일 제목, 할일 내용, 작성일, 작성자 이름 정보가 포함
* [x] **할 일 카드 목록 조회 기능**
    * 등록된 할 일 전체를 조회
    * 조회된 할 일 목록은 작성일 기준 내림차순으로 정렬
* [x] **선택한 할 일 수정 기능**
    * 선택한 할 일의 제목, 작성자명, 작성 내용을 수정 가능
    * 수정된 할 일의 정보를 반환 받아 확인 가능
* [x] **선택한 할 일 삭제 기능**
    * 선택한 게시글을 삭제 가능

### STEP 2

* [x] **할일카드 완료 기능 API**
    * 완료처리 한 할일 카드는 목록 조회 시 완료 여부 필드가 TRUE로 반환
    * 완료 여부 기본값은 FALSE
* [x] **댓글 작성 API**
    * 댓글 작성 시 ‘작성자 이름’과 ‘비밀번호’ 함께 받기
    * 선택한 할 일의 DB 저장 유무 확인 (todoId)
    * 댓글 등록 및 반환 (응답에서 비밀번호는 제외)
* [x] **댓글 수정 API**
    * 선택한 댓글의 DB 저장 유무 확인
    * 작성자 이름과 비밀번호를 함께 받아 저장된 값과 일치하면 수정 가능
    * 댓글 수정 및 반환 (응답에서 비밀번호는 제외)
* [x] **댓글 삭제 API**
    * 선택한 댓글의 DB 저장 유무 확인
    * 작성자 이름과 비밀번호를 함께 받아 저장된 값과 일치하면 삭제 가능
    * 댓글 삭제 시 성공 메시지와 상태코드 반환
* [x] **선택한 할 일 조회 기능 응답에 연관된 댓글 목록 추가**
    * 선택한 할 일 조회 API 응답에 연관된 댓글 목록 추가
    * 연관되지 않은 댓글은 포함되지 않음

### STEP 3-1 (필수 구현 기능)

* [x] 회원가입, 로그인 기능 JWT Token
* [x] 로그인 한 사용자
    * [x] 자신의 할 일 수정, 삭제
    * [x] 자신의 댓글 수정, 삭제

## STEP 4: Spring Plus 코드 개선 과제 진행 사항

### 이전 과제 피드백 반영 CODE REFACTORING
###### LOGIN 시 PASSWORD 검증 진행 절차 추가 (".takeIf { passwordEncoder.matches(password, it.password) }")
- E-MAIL을 검증한 이후, PASSWORD를 추가적으로 비교하여 일치하지 않을 시 throw IllegalArgumentException
- IllegalArgumentException 예외 발생 시 "Please check your email and password"

###### PACKAGE 구조에 대한 설정 변경
- COMMON PACKAGE 추가 (공통적으로 활용이 필요한 BaseTime Class, Exception을 COMMON PACKAGE 하위 설정)
- COMMENT 관련 PACKAGE TODO에 속하도록 PACKAGE 구조 변경 진행

###### DIET SERVICE
- 비즈니스 로직에 집중 기존 SERVICE LAYER 내 모두 구현해놓은 코드 역할 분담 진행
- 공통적으로 사용되는 예외처리 관련 METHOD 를 정의하여 필요한 METHOD마다 가져다 쓸 수 있도록 설정
- SERVICE에서 직접 Dto의 값을 꺼내 각각의 필드를 대입해주는 방식 모두 변경 진행
- Domain Entity가 Response DTO를 의존하지 않도록 변경 진행

### QueryDSL
###### QueryDSL 활용 'findAllWithFilters' Mehtod 구현
- 동적 쿼리 실습 진행을 위한 CATEGORY(ENUM CLASS), ISCOMPLETED(BOOLEAN) COLUMN 추가
- 아무것도 설정하지 않을 경우, CreatedAt Desc 순서로 모든 TodoCard 목록 조회
- 입력한 키워드가 TITLE 또는 DESCRIPTION에 포함되어 있는 TodoCard 목록 조회
- 입력한 CATEGORY에 해당하는 TodoCard 목록 조회
- ISCOMPLETED 상태 별 목록 조회
- 'orderSpecifier' TILTE 또는 USER NICKNAME을 순서로 조회 가능

### Test Code
###### Domain "Todo" Test Code 작성
- Repository Test QueryDsl을 활용한 'findAllWithFilters' Method TestCode 작성
- Service는 통합 테스트 방식으로 ~Repository 까지 함께 Test 진행 (상황 별 정상적으로 Update가 진행되는지, 예외 처리가 발생하는지 테스트 진행)
- Controller Test의 경우 Controller만 별도로 Test를 진행하기 때문에 @SpringBootTest 활용 Test 진행

### 과제를 진행하며 어려웠던 부분
- 'ControllerTest' 작성 과정에서, Service의 역할과 Controller의 역할을 명확하게 구분하여 Test 진행이 필요하나 해당 부분을 제대로 인지하지 못하고 있어 Service의 역할도 함께 Test를 진행하려다 보니 의존해야 하는 계층들이 지속적으로 확장
- PACKAGE 구조를 보다 유연하게, 구조화 된 틀이 아닌 다른 다양한 방식으로 구조를 설정하는 방식이 떠오르지 않아 결과적으로 구조에서 큰 변경없이 진행
- Test 시나리오 작성 관련, 뭐가 유의미한 테스트 시나리오인지 아직까지 모르겠음
- AWS S3 이미지 업로드 관련, 가입조차 많은 시간이 소요되어 추후 해당 과제 진행 예정
# 🔨 빌드 환경

* **Language:** Kotlin
* **IDE:** Intellij
* **SDK:** Eclipse Temurin 22.0.1
