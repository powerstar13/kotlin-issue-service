package study.project.issueservice.domain

import study.project.issueservice.domain.type.IssuePriority
import study.project.issueservice.domain.type.IssueStatus
import study.project.issueservice.domain.type.IssueType
import javax.persistence.*

@Entity
@Table
class Issue(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var userId: Long,

    @OneToMany(fetch = FetchType.EAGER)
    val comments: MutableList<Comment> = mutableListOf(),

    @Column
    var summary: String,

    @Column
    var description: String,

    @Column
    @Enumerated
    var type: IssueType,

    @Column
    @Enumerated
    var priority: IssuePriority,

    @Column
    @Enumerated
    var status: IssueStatus,

) : BaseEntity()