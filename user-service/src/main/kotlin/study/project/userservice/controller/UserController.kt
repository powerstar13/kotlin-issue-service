package study.project.userservice.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import study.project.userservice.dto.AuthToken
import study.project.userservice.dto.SignInRequest
import study.project.userservice.dto.SignInResponse
import study.project.userservice.dto.SignUpRequest
import study.project.userservice.service.UserService

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) =
        userService.signUp(request)

    @PostMapping("/signin")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse =
        userService.signIn(request)

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) =
        userService.logout(token)
}