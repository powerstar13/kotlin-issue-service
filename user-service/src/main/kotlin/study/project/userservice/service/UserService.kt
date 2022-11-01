package study.project.userservice.service

import org.springframework.stereotype.Service
import study.project.userservice.domain.User
import study.project.userservice.domain.UserRepository
import study.project.userservice.dto.SignUpRequest
import study.project.userservice.exception.UserExistsException
import study.project.userservice.util.security.BCryptUtil

@Service
class UserService(
    private val userRepository: UserRepository,
) {
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
}