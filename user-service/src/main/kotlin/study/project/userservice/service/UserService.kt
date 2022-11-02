package study.project.userservice.service

import org.springframework.stereotype.Service
import study.project.userservice.domain.User
import study.project.userservice.domain.UserRepository
import study.project.userservice.dto.SignInRequest
import study.project.userservice.dto.SignInResponse
import study.project.userservice.dto.SignUpRequest
import study.project.userservice.exception.PasswordNotMatchedException
import study.project.userservice.exception.UserExistsException
import study.project.userservice.exception.UserNotFoundException
import study.project.userservice.util.jwt.JwtClaim
import study.project.userservice.util.jwt.JwtProperties
import study.project.userservice.util.jwt.JwtUtil
import study.project.userservice.util.security.BCryptUtil
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JwtProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {
    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) =
        with(signUpRequest) {

            userRepository.findByEmail(email)
                ?.let {
                    throw UserExistsException()
                }

            val user = User(
                email = email,
                password = BCryptUtil.hash(password),
                username = username,
            )
            userRepository.save(user)
        }

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse =
        with(
            userRepository.findByEmail(signInRequest.email)
                ?: throw UserNotFoundException()
        ) {
            val verified = BCryptUtil.verify(signInRequest.password, password)
            if (!verified) throw PasswordNotMatchedException()

            val jwtClaim = JwtClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JwtUtil.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }

    suspend fun logout(token: String) =
        cacheManager.awaitEvict(token)
}