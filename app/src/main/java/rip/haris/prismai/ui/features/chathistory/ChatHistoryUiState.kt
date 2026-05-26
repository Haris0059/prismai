package rip.haris.prismai.ui.features.chathistory

import rip.haris.prismai.domain.model.Chat
import rip.haris.prismai.ui.common.LoadStatus

data class ChatHistoryUiState(
    val searchQuery: String = "",
    val chats: List<Chat> = emptyList(),
    val status: LoadStatus = LoadStatus.Init,
) {
    val filteredChats: List<Chat>
        get() = if (searchQuery.isBlank()) chats
                else chats.filter { it.title.contains(searchQuery, ignoreCase = true) }

    val isEmpty: Boolean
        get() = filteredChats.isEmpty()
}
