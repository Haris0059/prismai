package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import rip.haris.prismai.domain.repository.ChatRepository
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.presentation.ui.common.LoadStatus
import rip.haris.prismai.presentation.ui.screens.chathistory.ChatHistoryUiState

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatHistoryUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ChatHistoryUiState> = _uiState.asStateFlow()

    init {
        userRepository.observeCurrentUser()
            .flatMapLatest { user ->
                if (user == null) flowOf(emptyList()) else chatRepository.observeChats(user.id)
            }
            .onEach { chats ->
                _uiState.update { it.copy(chats = chats, status = LoadStatus.Success) }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
