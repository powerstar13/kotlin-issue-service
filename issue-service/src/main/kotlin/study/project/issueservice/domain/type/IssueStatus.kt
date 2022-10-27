package study.project.issueservice.domain.type

enum class IssueStatus(val description: String) {

    TODO("할일"),
    IN_PROGRESS("진행중"),
    RESOLVED("완료");

    companion object {

        operator fun invoke(status: String) = valueOf(status.uppercase())
    }
}
