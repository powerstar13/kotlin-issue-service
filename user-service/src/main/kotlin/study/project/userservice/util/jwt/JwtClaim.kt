package study.project.userservice.util.jwt

data class JwtClaim(
    val userId: Long,
    val email: String,
    val profileUrl: String? = null,
    val username: String,
)
