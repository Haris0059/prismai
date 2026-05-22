package rip.haris.prismai.domain.model

data class UserPreference(
    val id: Long = 0,
    val userId: Long,
    val key: String,
    val value: String,
)
