package rip.haris.prismai.ui.features.chathistory

import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.ui.common.LoadStatus

data class ChatHistoryUiState(
    val searchQuery: String = "",
    val conversations: List<Conversation> = emptyList(),
    val renameTarget: Conversation? = null,
    val renameText: String = "",
    val status: LoadStatus = LoadStatus.Init,
) {
    val filteredChats: List<Conversation>
        get() = if (searchQuery.isBlank()) conversations
                else conversations.filter { it.title.contains(searchQuery, ignoreCase = true) }

    val isEmpty: Boolean
        get() = filteredChats.isEmpty()

    val isLoading: Boolean
        get() = status is LoadStatus.Loading

    val errorMessage: String?
        get() = (status as? LoadStatus.Error)?.message
}
