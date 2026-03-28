package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rip.haris.prismai.data.model.ChatHistoryState

class ChatHistoryViewModel : ViewModel() {
    private val _state = MutableStateFlow(ChatHistoryState())
    val state: StateFlow<ChatHistoryState> = _state

    fun onSearchQueryChange(query: String) {
        val filtered = if (query.isBlank()) {
            _state.value.chats
        } else {
            _state.value.chats.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        _state.value = _state.value.copy(
            searchQuery = query,
            filteredChats = filtered
        )
    }
}
