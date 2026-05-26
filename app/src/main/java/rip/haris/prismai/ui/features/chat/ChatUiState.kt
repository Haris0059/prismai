package rip.haris.prismai.ui.features.chat

import rip.haris.prismai.domain.model.AiModel
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.ui.common.LoadStatus

data class ChatUiState(
    val chatId: Long? = null,
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val selectedModel: AiModel? = null,
    val availableModels: List<AiModel> = emptyList(),
    val greeting: String = "",
    val showModelSheet: Boolean = false,
    val suggestedPrompts: List<String> = DEFAULT_SUGGESTED_PROMPTS,
    val status: LoadStatus = LoadStatus.Init,
) {
    val canSend: Boolean
        get() = inputText.isNotBlank() && selectedModel != null

    companion object {
        val DEFAULT_SUGGESTED_PROMPTS = listOf(
            "Explain recursion simply",
            "Summarize this article",
            "Write a unit test",
            "Debug my code",
            "Plan my week",
            "Brainstorm project names",
            "Draft an email",
            "Review my resume",
        )
    }
}
