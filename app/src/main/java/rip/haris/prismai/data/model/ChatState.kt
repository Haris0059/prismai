package rip.haris.prismai.data.model

val greetings = listOf(
    "How can I help you\ntoday?",
    "How can I help you\nthis evening?",
    "What can I help\nwith?",
    "What's on your\nmind?",
    "Ready when\nyou are."
)

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val selectedModel: String = "Opus 4.6",
    val showModelSheet: Boolean = false,
    val greeting: String = greetings.random()
)
