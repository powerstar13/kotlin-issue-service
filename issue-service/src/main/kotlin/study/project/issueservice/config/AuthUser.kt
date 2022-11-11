package study.project.issueservice.config

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthUser(
    @JsonProperty("id")
    val userId: Long,
    val username: String,
    val email: String,
    val profileUrl: String? = null,
)