package study.project.issueservice.domain.type

enum class IssueType {

    BUG, TASK;

    companion object {
        // operator invoke 함수를 정의하면 생성자를 사용하듯이 사용할 수 있다.
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}