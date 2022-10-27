package study.project.issueservice.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.project.issueservice.domain.Issue
import study.project.issueservice.domain.IssueRepository
import study.project.issueservice.dto.IssueRequest
import study.project.issueservice.dto.IssueResponse

@Service
class IssueService(
    private val issueRepository: IssueRepository
) {
    @Transactional
    fun create(
        userId: Long,
        request: IssueRequest
    ): IssueResponse {

        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }
}