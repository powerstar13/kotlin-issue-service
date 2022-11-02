package study.project.userservice.dto

import study.project.userservice.domain.User
import java.time.LocalDateTime

data class MeResponse(
    val id: Long,
    val profileUrl: String?,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {
    companion object {

        operator fun invoke(user: User) =
            with(user) {
                MeResponse(
                    id = id!!,
                    profileUrl = if (profileUrl.isNullOrEmpty()) null else "http://localhost:9090/images/$profileUrl",
                    username = username,
                    email = email,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}