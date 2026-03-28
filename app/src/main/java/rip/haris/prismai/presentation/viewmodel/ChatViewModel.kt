package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rip.haris.prismai.data.model.ChatMessage
import rip.haris.prismai.data.model.ChatState

class ChatViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    fun onInputChange(text: String) {
        _state.update { it.copy(inputText = text) }
    }

    fun onSendMessage() {
        val text = _state.value.inputText.trim()
        if (text.isBlank()) return

        _state.update {
            it.copy(
                messages = it.messages + ChatMessage(text = text, isUser = true),
                inputText = ""
            )
        }

        // Simulated AI response
        _state.update {
            it.copy(
                messages = it.messages + ChatMessage(
                    text = "This is a simulated AI response. In a real app, this would come from an API.",
                    isUser = false
                )
            )
        }
    }

    fun onModelSelected(model: String) {
        _state.update { it.copy(selectedModel = model, showModelSheet = false) }
    }

    fun onNewChat() {
        _state.update { it.copy(messages = emptyList(), inputText = "") }
    }

    fun onShowModelSheet(show: Boolean) {
        _state.update { it.copy(showModelSheet = show) }
    }
}
