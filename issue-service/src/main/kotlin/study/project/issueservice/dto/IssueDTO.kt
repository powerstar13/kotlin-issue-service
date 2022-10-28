package study.project.issueservice.dto

import com.fasterxml.jackson.annotation.JsonFormat
import study.project.issueservice.domain.Comment
import study.project.issueservice.domain.Issue
import study.project.issueservice.domain.type.IssuePriority
import study.project.issueservice.domain.type.IssueStatus
import study.project.issueservice.domain.type.IssueType
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    val comments: List<CommentResponse> = emptyList(),
    val summary: String,
    val description: String,
    val userId: Long,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {

        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    comments = comments.sortedByDescending(Comment::id)
                        .map(Comment::toResponse),
                    summary = summary,
                    description = description,
                    userId = userId,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = createdAt
                )
            }
    }
}