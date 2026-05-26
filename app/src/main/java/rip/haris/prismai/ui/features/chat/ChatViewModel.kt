package rip.haris.prismai.ui.features.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rip.haris.prismai.data.session.NewChatEvent
import rip.haris.prismai.domain.model.Chat
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.domain.repository.AiModelRepository
import rip.haris.prismai.domain.repository.ChatRepository
import rip.haris.prismai.domain.repository.GreetingRepository
import rip.haris.prismai.domain.repository.MessageRepository
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.ui.navigation.Routes
import rip.haris.prismai.ui.common.LoadStatus
import rip.haris.prismai.ui.features.chat.ChatUiState

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val aiModelRepository: AiModelRepository,
    private val greetingRepository: GreetingRepository,
    private val userRepository: UserRepository,
    private val newChatEvent: NewChatEvent,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val currentChatId = MutableStateFlow(
        savedStateHandle.get<String>(Routes.ARG_CHAT_ID)?.toLongOrNull()
    )

    init {
        // Load a random greeting once on creation.
        viewModelScope.launch {
            val greeting = greetingRepository.getRandom()?.text.orEmpty()
            _uiState.update { it.copy(greeting = greeting) }
        }

        // Observe available AI models; keep selectedModel pointing at a valid one.
        aiModelRepository.observeAll()
            .onEach { models ->
                _uiState.update { state ->
                    state.copy(
                        availableModels = models,
                        selectedModel = state.selectedModel ?: models.firstOrNull(),
                        status = LoadStatus.Success,
                    )
                }
            }
            .launchIn(viewModelScope)

        // Observe messages for whichever chat is currently selected.
        currentChatId
            .flatMapLatest { id ->
                if (id == null) flowOf(emptyList()) else messageRepository.observeMessages(id)
            }
            .onEach { messages -> _uiState.update { it.copy(messages = messages, chatId = currentChatId.value) } }
            .launchIn(viewModelScope)

        // Reset to a fresh chat (new greeting, no chatId) whenever the drawer fires "New chat".
        newChatEvent.events
            .onEach { onNewChat() }
            .launchIn(viewModelScope)
    }

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onSelectModel(modelName: String) {
        val model = _uiState.value.availableModels.firstOrNull { it.name == modelName }
        _uiState.update { it.copy(selectedModel = model ?: it.selectedModel, showModelSheet = false) }
    }

    fun onShowModelSheet(show: Boolean) {
        _uiState.update { it.copy(showModelSheet = show) }
    }

    fun onSendMessage() {
        val text = _uiState.value.inputText.trim()
        if (text.isBlank()) return
        viewModelScope.launch {
            val user = userRepository.observeCurrentUser().first { it != null } ?: return@launch
            val modelId = _uiState.value.selectedModel?.id
            val now = System.currentTimeMillis()

            val chatId = currentChatId.value ?: chatRepository.createChat(
                Chat(
                    userId = user.id,
                    modelId = modelId,
                    title = text.take(60),
                    createdAt = now,
                    updatedAt = now,
                )
            ).also { newId -> currentChatId.value = newId }

            _uiState.update { it.copy(inputText = "") }

            messageRepository.insert(Message(chatId = chatId, text = text, isUser = true, createdAt = now))
            chatRepository.touch(chatId, now)

            // Simulated AI response — networking comes in a later assignment.
            val replyAt = System.currentTimeMillis()
            messageRepository.insert(
                Message(
                    chatId = chatId,
                    text = "This is a simulated AI response. In a real app, this would come from an API.",
                    isUser = false,
                    createdAt = replyAt,
                )
            )
            chatRepository.touch(chatId, replyAt)
        }
    }

    fun onNewChat() {
        currentChatId.value = null
        viewModelScope.launch {
            val greeting = greetingRepository.getRandom()?.text.orEmpty()
            _uiState.update {
                it.copy(chatId = null, messages = emptyList(), inputText = "", greeting = greeting)
            }
        }
    }

}
