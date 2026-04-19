package rip.haris.prismai.data.model

data class ChatHistoryItem(
    val id: String,
    val title: String,
    val timeAgo: String
)

data class ChatHistoryState(
    val searchQuery: String = "",
    val chats: List<ChatHistoryItem> = HardcodedData.sampleChats,
    val filteredChats: List<ChatHistoryItem> = HardcodedData.sampleChats
)
