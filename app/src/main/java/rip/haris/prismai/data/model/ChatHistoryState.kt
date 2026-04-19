package rip.haris.prismai.data.model

data class ChatHistoryItem(
    val id: String,
    val title: String,
    val timeAgo: String
)

data class ChatHistoryState(
    val searchQuery: String = "",
    val chats: List<ChatHistoryItem> = sampleChats,
    val filteredChats: List<ChatHistoryItem> = sampleChats
)
val sampleChats = listOf(
    ChatHistoryItem("chat-01", "No seriously, why is it working?", "2 minutes ago"),
    ChatHistoryItem("chat-02", "Why is my code working?", "8 minutes ago"),
    ChatHistoryItem("chat-03", "Best practices for REST API design", "1 hour ago"),
    ChatHistoryItem("chat-04", "Explain recursion like I'm 5", "2 hours ago"),
    ChatHistoryItem("chat-05", "Explain recursion like I'm 3", "2 hours ago"),
    ChatHistoryItem("chat-06", "Is O(n²) really that bad? (it is)", "5 hours ago"),
    ChatHistoryItem("chat-07", "My professor said this is wrong but it runs", "7 hours ago"),
    ChatHistoryItem("chat-08", "Clean architecture in Android with Jetpack", "8 hours ago"),
    ChatHistoryItem("chat-09", "Writing a README nobody will read", "9 hours ago"),
    ChatHistoryItem("chat-10", "How to center a div (still)", "10 hours ago"),
    ChatHistoryItem("chat-11", "How to bypass a SEB", "12 hours ago"),
    ChatHistoryItem("chat-12", "Unit tests or just hope for the best?", "Yesterday"),
    ChatHistoryItem("chat-13", "My merge conflict has merge conflicts", "2 days ago"),
    ChatHistoryItem("chat-14", "Ethics in software engineering essay help", "1 week ago"),
    ChatHistoryItem("chat-15", "Debugging at 2am, a love story", "1 week ago"),
    ChatHistoryItem("chat-16", "My app works on emulator, not on phone", "1 week ago"),
    ChatHistoryItem("chat-17", "How to NOT get caught cheating", "1 week ago"),
    ChatHistoryItem("chat-18", "How to cheat on assignments", "2 week ago"),
)
