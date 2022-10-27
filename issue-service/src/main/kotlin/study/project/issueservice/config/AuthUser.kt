package study.project.issueservice.config

data class AuthUser(
    val userId: Long,
    val username: String,
    val profileUrl: String? = null,
)