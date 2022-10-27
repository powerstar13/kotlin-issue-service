package study.project.issueservice.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): ErrorResponse {

        logger.error { ex.message }

        return ErrorResponse(code = ex.code, message = ex.message)
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(ex: TokenExpiredException): ErrorResponse {

        logger.error { ex.message }

        return ErrorResponse(code = 401, message = "Token Expired Error")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ErrorResponse {

        logger.error { ex.message }

        // WARN: 예상치 못한 에러에 대한 정보를 그대로 내려주게 되면 해커에게 악용될 수 있으므로 반드시 서버 에러의 메시지는 핸들링해야 한다.
        return ErrorResponse(code = 500, message = "Internal Server Error")
    }
}