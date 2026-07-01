package rip.haris.prismai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rip.haris.prismai.data.session.NewChatEvent
import rip.haris.prismai.data.session.SessionManager
import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.domain.model.User
import rip.haris.prismai.domain.repository.ConversationRepository
import rip.haris.prismai.domain.repository.UserRepository

@HiltViewModel
class RootViewModel @Inject constructor(
    userRepository: UserRepository,
    private val conversationRepository: ConversationRepository,
    private val sessionManager: SessionManager,
    private val newChatEvent: NewChatEvent,
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = sessionManager.isLoggedIn

    fun logout() = sessionManager.signOut()

    fun startNewChat() = newChatEvent.fire()

    val currentUser: StateFlow<User?> = userRepository.observeCurrentUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _recentChats = MutableStateFlow<List<Conversation>>(emptyList())
    val recentChats: StateFlow<List<Conversation>> = _recentChats.asStateFlow()

    init {
        // Load recent conversations from the backend whenever the user is signed in.
        sessionManager.isLoggedIn
            .onEach { loggedIn -> if (loggedIn) refreshRecents() else _recentChats.value = emptyList() }
            .launchIn(viewModelScope)
    }

    /** Re-fetches the recent conversations (e.g. when the drawer is opened). */
    fun refreshRecents() {
        viewModelScope.launch {
            runCatching { conversationRepository.getConversations() }
                .onSuccess { _recentChats.value = it.take(11) }
        }
    }
}
