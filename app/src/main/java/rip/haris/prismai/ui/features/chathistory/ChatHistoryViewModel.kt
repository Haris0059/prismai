package rip.haris.prismai.ui.features.chathistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.domain.repository.ConversationRepository
import rip.haris.prismai.ui.common.LoadStatus

@HiltViewModel
class ChatHistoryViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatHistoryUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ChatHistoryUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = LoadStatus.Loading) }
            runCatching { conversationRepository.getConversations() }
                .onSuccess { conversations ->
                    _uiState.update { it.copy(conversations = conversations, status = LoadStatus.Success) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(status = LoadStatus.Error(e.toMessage())) }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onRenameClick(conversation: Conversation) {
        _uiState.update { it.copy(renameTarget = conversation, renameText = conversation.title) }
    }

    fun onRenameTextChange(text: String) {
        _uiState.update { it.copy(renameText = text) }
    }

    fun onRenameDismiss() {
        _uiState.update { it.copy(renameTarget = null, renameText = "") }
    }

    fun onRenameConfirm() {
        val target = _uiState.value.renameTarget ?: return
        val newTitle = _uiState.value.renameText.trim()
        if (newTitle.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(renameTarget = null, renameText = "") }
            runCatching { conversationRepository.rename(target.id, newTitle) }
                .onSuccess { refresh() }
                .onFailure { e -> _uiState.update { it.copy(status = LoadStatus.Error(e.toMessage())) } }
        }
    }

    fun onDelete(conversation: Conversation) {
        viewModelScope.launch {
            runCatching { conversationRepository.delete(conversation.id) }
                .onSuccess { refresh() }
                .onFailure { e -> _uiState.update { it.copy(status = LoadStatus.Error(e.toMessage())) } }
        }
    }

    private fun Throwable.toMessage(): String = when (this) {
        is HttpException -> when (code()) {
            401 -> "Session expired. Please sign in again."
            404 -> "Conversation not found."
            else -> "Server error (${code()})."
        }
        is IOException -> "No internet connection."
        else -> message ?: "Something went wrong."
    }
}
