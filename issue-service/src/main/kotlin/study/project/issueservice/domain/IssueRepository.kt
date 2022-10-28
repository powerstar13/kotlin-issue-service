package study.project.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository
import study.project.issueservice.domain.type.IssueStatus

interface IssueRepository : JpaRepository<Issue, Long> {
    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus): List<Issue>?
}