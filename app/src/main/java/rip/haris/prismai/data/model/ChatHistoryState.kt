package rip.haris.prismai.data.model

data class ChatHistoryItem(
    val title: String,
    val timeAgo: String
)

data class ChatHistoryState(
    val searchQuery: String = "",
    val chats: List<ChatHistoryItem> = sampleChats,
    val filteredChats: List<ChatHistoryItem> = sampleChats
)

val sampleChats = listOf(
    ChatHistoryItem("Diamond blade label modifications", "1 hour ago"),
    ChatHistoryItem("Test", "5 hours ago"),
    ChatHistoryItem("Improving commit message skill auto-det...", "8 hours ago"),
    ChatHistoryItem("OpenAI models comparable to Claude O...", "8 hours ago"),
    ChatHistoryItem("Testing purposes", "9 hours ago"),
    ChatHistoryItem("Building multiple components in Claude", "9 hours ago"),
    ChatHistoryItem("Scaling user platform to 10 million", "15 hours ago"),
    ChatHistoryItem("G435 headphones microphone not worki...", "2 days ago"),
    ChatHistoryItem("Answering student questions", "2 days ago"),
    ChatHistoryItem("Product photo prompt for a...", "2 days ago"),
    ChatHistoryItem("Professional portfolio website design", "3 days ago"),
    ChatHistoryItem("Kotlin coroutine best practices", "3 days ago")
)
