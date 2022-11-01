package study.project.userservice.exception

import org.springframework.http.HttpStatus

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistsException(
    override val message: String = "이미 존재하는 유저입니다."
) : ServerException(HttpStatus.CONFLICT.value(), message)
