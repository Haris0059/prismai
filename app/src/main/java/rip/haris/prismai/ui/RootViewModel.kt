package rip.haris.prismai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import rip.haris.prismai.data.session.NewChatEvent
import rip.haris.prismai.data.session.SessionManager
import rip.haris.prismai.domain.model.Chat
import rip.haris.prismai.domain.model.User
import rip.haris.prismai.domain.repository.ChatRepository
import rip.haris.prismai.domain.repository.UserRepository

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RootViewModel @Inject constructor(
    userRepository: UserRepository,
    chatRepository: ChatRepository,
    private val sessionManager: SessionManager,
    private val newChatEvent: NewChatEvent,
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = sessionManager.isLoggedIn

    fun logout() = sessionManager.logout()

    fun startNewChat() = newChatEvent.fire()

    val currentUser: StateFlow<User?> = userRepository.observeCurrentUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val recentChats: StateFlow<List<Chat>> = userRepository.observeCurrentUser()
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList()) else chatRepository.observeChats(user.id)
        }
        .map { it.take(11) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
