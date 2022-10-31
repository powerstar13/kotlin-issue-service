# 이슈 관리 서비스

## 공통 에러 처리

1. API를 개발할 땐 에러가 발생했을 때의 상황을 고려해 항상 일관된 에러 처리 규칙을 정해야 한다.
2. 에러 응답 모델 정의
    - code로 어떤 에러가 발생했는지 정의한다.
        - HTTP status code만으로 모든 에러에 대한 케이스를 처리하기 어렵다.
    - 에러가 발생했을 때 나타낼 메시지를 표시한다.
        - 메시지에는 stacktrace와 같이 언어, 프레임워크 정보, DB 필드 등이 노출되면 절대 안 된다.
3. Exception 구조 정의
4. GlobalExceptionHandler 정의
    1. `@RestControllerAdvice` 또는 `@ControllerAdvice`를 사용하면 Controller에서 발생하는 모든 예외를 감지할 수 있다.
    2. ExceptionHandler에서 정해진 ErrorResponse 객체로 응답하게 되면 항상 일관성있는 에러 처리를 할 수 있게 된다.

## 이슈 등록

1. 도메인 모델링
   1. 공통 엔티티로 만들 BaseEntity 추가
      1. **모든 엔티티에서 사용할 공통된 속성을 정의하고 싶은 경우, `@MappedSuperclass`를 사용해 부모 엔티티에서 공통 속성을 정의하고 하위 엔티티에서 상속받아 사용할 수 있다.**
      2. `@EntityListeners`: 엔티티에 특정한 이벤트가 발생하면 정해진 콜백 처리를 할 수 있다.
      3. `AuditingEventListener`를 사용하면 엔티티가 생성될 때 `@CreatedDate`가 붙은 프로퍼티에 생성일시를 자동으로 넣어주고, 엔티티에 변경이 일어나면 `@LastModifiedDate`가 붙은 프로퍼티에 변경일시를 자동으로 넣어주게 된다.
   2. `@EnableJpaAuditing`: AuditingEventListener를 사용하려면 `@EnableJpaAuditing` 애노테이션을 MainApplication에 추가해야 한다.
   3. 이슈 관리 서비스의 핵심 엔티티인 이슈 엔티티 생성
2. 리포지토리 생성
3. 서비스 생성
   1. request, response DTO를 생성
4. 컨트롤러 생성
5. API 테스트

## 이슈 목록 조회

1. 컨트롤러 작성
2. 서비스 작성
3. 리포지토리 작성
4. API 테스트

## 이슈 상세 조회

1. 컨트롤러 작성
2. 서비스 작성
3. API 테스트

## 이슈 수정

1. 컨트롤러 작성
2. 서비스 작성
3. API 테스트

## 이슈 삭제

1. 컨트롤러 작성
2. 서비스 작성
3. API 테스트

## 코멘트 등록

1. 도메인 모델링
2. 리포지토리 생성
3. 서비스 생성
4. DTO 생성
   1. 기존 IssueModel은 `operator fun invoke`를 재정의해서 사용했지만, Comment 모델에선 확장함수를 사용해서 toResponse를 정의한다.
   2. 실무에선 이와 같이 다양한 방식을 사용하므로 팀내 컨벤션이 있다면 그 방식을 따르는 것이 좋다.
5. 컨트롤러 생성
6. API 테스트

## 코멘트 수정

1. 컨트롤러 작성
2. 서비스 작성
3. 리포지토리 작성
4. API 테스트

## 코멘트 삭제

1. 컨트롤러 작성
2. 서비스 작성
3. API 테스트

## 회원 인증 서비스 프로젝트 구성하기

1. user-service 모듈 생성
2. kapt 플러그인 추가
3. WebFlux & Coroutine & R2DBC 의존성 사용
4. schema.sql 작성
5. server port 변경
6. R2DBC Config 작성
7. CORS 처리
