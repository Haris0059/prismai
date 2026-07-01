package rip.haris.prismai.ui.features.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rip.haris.prismai.data.session.NewChatEvent
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.domain.repository.AiModelRepository
import rip.haris.prismai.domain.repository.ConversationRepository
import rip.haris.prismai.domain.repository.GreetingRepository
import rip.haris.prismai.ui.common.LoadStatus
import rip.haris.prismai.ui.navigation.Routes

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val aiModelRepository: AiModelRepository,
    private val greetingRepository: GreetingRepository,
    private val newChatEvent: NewChatEvent,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    // Server conversation id (UUID) for the current thread; null for a brand-new chat.
    private var conversationId: String? = null
    private var loadJob: Job? = null

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
                    )
                }
            }
            .launchIn(viewModelScope)

        // React to the selected conversation. Observing the nav arg as a flow means a
        // reused ViewModel (launchSingleTop on chatDetail) still reloads when the id changes.
        savedStateHandle.getStateFlow<String?>(Routes.ARG_CHAT_ID, null)
            .onEach { id ->
                conversationId = id
                if (id.isNullOrBlank()) {
                    _uiState.update { it.copy(chatId = null, messages = emptyList(), status = LoadStatus.Success) }
                } else {
                    loadConversation(id)
                }
            }
            .launchIn(viewModelScope)

        // Reset to a fresh chat whenever the drawer / FAB fires "New chat".
        newChatEvent.events
            .onEach { onNewChat() }
            .launchIn(viewModelScope)
    }

    private fun loadConversation(id: String) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(messages = emptyList(), status = LoadStatus.Loading) }
            runCatching { conversationRepository.getMessages(id) }
                .onSuccess { messages ->
                    _uiState.update { it.copy(messages = messages, chatId = id, status = LoadStatus.Success) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(status = LoadStatus.Error(e.message ?: "Failed to load chat")) }
                }
        }
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

        val (provider, model) = resolveProviderModel(_uiState.value.selectedModel?.name)
        val now = System.currentTimeMillis()

        // Optimistically show the user's message and clear the input.
        _uiState.update {
            it.copy(
                messages = it.messages + Message(chatId = 0, text = text, isUser = true, createdAt = now),
                inputText = "",
            )
        }

        viewModelScope.launch {
            runCatching {
                conversationRepository.sendMessage(provider, model, text, conversationId)
            }.onSuccess { reply ->
                conversationId = reply.conversationId
                val replyMsg = Message(
                    chatId = 0,
                    text = reply.text.ifBlank { "(The assistant returned no text.)" },
                    isUser = false,
                    createdAt = System.currentTimeMillis(),
                )
                _uiState.update { it.copy(messages = it.messages + replyMsg, chatId = reply.conversationId) }
            }.onFailure { e ->
                val errMsg = Message(
                    chatId = 0,
                    text = "⚠️ Couldn't reach the server: ${e.message ?: "unknown error"}",
                    isUser = false,
                    createdAt = System.currentTimeMillis(),
                )
                _uiState.update { it.copy(messages = it.messages + errMsg) }
            }
        }
    }

    fun onNewChat() {
        conversationId = null
        viewModelScope.launch {
            val greeting = greetingRepository.getRandom()?.text.orEmpty()
            _uiState.update {
                it.copy(chatId = null, messages = emptyList(), inputText = "", greeting = greeting, status = LoadStatus.Success)
            }
        }
    }

    /** Maps a display model name to the backend provider + model identifier. */
    private fun resolveProviderModel(modelName: String?): Pair<String, String> = when {
        modelName == null -> DEFAULT_PROVIDER_MODEL
        modelName.contains("GPT", ignoreCase = true) -> "openai" to "gpt-4o-mini"
        modelName.contains("Gemini", ignoreCase = true) -> "gemini" to "gemini-1.5-pro"
        else -> DEFAULT_PROVIDER_MODEL
    }

    private companion object {
        val DEFAULT_PROVIDER_MODEL = "anthropic" to "claude-haiku-4-5-20251001"
    }
}
