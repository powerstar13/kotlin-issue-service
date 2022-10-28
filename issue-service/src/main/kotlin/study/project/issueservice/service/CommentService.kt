package study.project.issueservice.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.project.issueservice.domain.Comment
import study.project.issueservice.domain.CommentRepository
import study.project.issueservice.domain.IssueRepository
import study.project.issueservice.dto.CommentRequest
import study.project.issueservice.dto.CommentResponse
import study.project.issueservice.dto.toResponse
import study.project.issueservice.exception.NotFoundException

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(
        issueId: Long,
        userId: Long,
        username: String,
        request: CommentRequest
    ): CommentResponse {

        val issue = issueRepository.findByIdOrNull(issueId)
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )

        issue.comments.add(comment)

        return commentRepository.save(comment)
            .toResponse()
    }
}