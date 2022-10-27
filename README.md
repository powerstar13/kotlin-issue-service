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

