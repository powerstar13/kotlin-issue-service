package study.project.issueservice.domain.type

enum class IssuePriority {

    LOW, MEDIUM, HIGH;

    companion object {

        operator fun invoke(priority: String) = valueOf(priority.uppercase())
    }
}
