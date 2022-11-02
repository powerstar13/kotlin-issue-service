package study.project.userservice.controller

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import study.project.userservice.dto.*
import study.project.userservice.service.UserService
import java.io.File

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

    @GetMapping("/me")
    suspend fun get(@AuthToken token: String): MeResponse =
        MeResponse(userService.getByToken(token))

    @GetMapping("/{userId}/username")
    suspend fun getUsername(@PathVariable userId: Long): Map<String, String> =
        mapOf("reporter" to userService.getUser(userId).username)

    @PostMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun edit(
        @PathVariable id: Long,
        @ModelAttribute request: UserEditRequest, // multipart/form-data이기 때문에 RequestBody가 아닌 ModelAttribute로 받는다.
        @AuthToken token: String,
        @RequestPart("profileUrl") filePart: FilePart,
    ) {
        val originFilename = filePart.filename()
        var filename: String? = null

        if (originFilename.isNotEmpty()) {

            val ext = originFilename.substring(originFilename.lastIndexOf(".") + 1) // 확장자 가져오기
            filename = "${id}.${ext}" // 사용자일련번호.확장자

            val file = File(ClassPathResource("/images").file, filename)
            filePart.transferTo(file)
                .awaitSingleOrNull()
        }

        userService.edit(token, request.username, filename)
    }
}