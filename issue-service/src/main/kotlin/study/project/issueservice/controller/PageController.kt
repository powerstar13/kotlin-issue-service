package study.project.issueservice.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping(value = ["", "/index"])
    fun index() = "index" // resources/templates/index.html에 접근하 수 있다.

    @GetMapping("/issueapp")
    fun issueApp() = "issueapp"

    @GetMapping("/signup")
    fun signUp() = "signup"
}