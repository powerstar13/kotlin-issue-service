package study.project.issueservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.project.issueservice.domain.Issue
import study.project.issueservice.domain.IssueRepository
import study.project.issueservice.domain.type.IssueStatus
import study.project.issueservice.dto.IssueRequest
import study.project.issueservice.dto.IssueResponse
import study.project.issueservice.exception.NotFoundException

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

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }

    @Transactional(readOnly = true)
    fun get(id: Long): IssueResponse {

        val issue = issueRepository.findByIdOrNull(id)
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

        return IssueResponse(issue)
    }

    @Transactional
    fun edit(userId: Long, issueId: Long, request: IssueRequest): Any {

        val issue = issueRepository.findByIdOrNull(issueId)
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

        return with(issue) {
            this.summary = request.summary
            this.description = request.description
            this.userId = userId
            this.type = request.type
            this.priority = request.priority
            this.status = request.status

            IssueResponse(issueRepository.save(this))
        }
    }
}