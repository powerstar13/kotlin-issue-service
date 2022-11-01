package study.project.userservice.dto

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
)