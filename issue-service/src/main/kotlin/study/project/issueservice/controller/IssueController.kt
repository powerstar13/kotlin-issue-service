package study.project.issueservice.controller

import org.springframework.web.bind.annotation.*
import study.project.issueservice.config.AuthUser
import study.project.issueservice.domain.type.IssueStatus
import study.project.issueservice.dto.IssueRequest
import study.project.issueservice.service.IssueService

@RestController
@RequestMapping("/api/v1/issues")
class IssueController(
    private val issueService: IssueService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) =
        issueService.create(authUser.userId, request)

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam(required = false, defaultValue = "TODO") status: IssueStatus,
    ) =
        issueService.getAll(status)

    @GetMapping("/{id}")
    fun get(
        authUser: AuthUser,
        @PathVariable id: Long,
    ) =
        issueService.get(id)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: IssueRequest
    ) =
        issueService.edit(authUser.userId, id, request)
}