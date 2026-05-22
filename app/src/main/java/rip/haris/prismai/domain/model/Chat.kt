package rip.haris.prismai.domain.model

data class Chat(
    val id: Long = 0,
    val userId: Long,
    val modelId: Long?,
    val title: String = "New chat",
    val createdAt: Long,
    val updatedAt: Long,
)
