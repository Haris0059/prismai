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
    ChatHistoryItem("No seriously, why is it working?", "2 minutes ago"),
    ChatHistoryItem("Why is my code working?", "8 minutes ago"),
    ChatHistoryItem("Best practices for REST API design", "1 hour ago"),
    ChatHistoryItem("Explain recursion like I'm 5", "2 hours ago"),
    ChatHistoryItem("Explain recursion like I'm 3", "2 hours ago"),
    ChatHistoryItem("Is O(n²) really that bad? (it is)", "5 hours ago"),
    ChatHistoryItem("My professor said this is wrong but it runs", "7 hours ago"),
    ChatHistoryItem("Clean architecture in Android with Jetpack", "8 hours ago"),
    ChatHistoryItem("Writing a README nobody will read", "9 hours ago"),
    ChatHistoryItem("How to center a div (still)", "10 hours ago"),
    ChatHistoryItem("How to bypass a SEB", "12 hours ago"),
    ChatHistoryItem("Unit tests or just hope for the best?", "Yesterday"),
    ChatHistoryItem("My merge conflict has merge conflicts", "2 days ago"),
    ChatHistoryItem("Ethics in software engineering essay help", "1 week ago"),
    ChatHistoryItem("Debugging at 2am, a love story", "1 week ago"),
    ChatHistoryItem("My app works on emulator, not on phone", "1 week ago"),
    ChatHistoryItem("How to NOT get caught cheating", "1 week ago"),
    ChatHistoryItem("How to cheat on assignments", "2 week ago"),
)
