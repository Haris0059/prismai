package rip.haris.prismai.domain.model

data class Message(
    val id: Long = 0,
    val chatId: Long,
    val text: String,
    val isUser: Boolean,
    val createdAt: Long,
)
