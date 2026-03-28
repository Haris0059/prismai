package rip.haris.prismai.data.model

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val selectedModel: String = "Opus 4.6",
    val showModelSheet: Boolean = false
)
