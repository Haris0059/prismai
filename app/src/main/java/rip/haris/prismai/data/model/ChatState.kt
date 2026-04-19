package rip.haris.prismai.data.model

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val selectedModel: String = HardcodedData.DEFAULT_SELECTED_MODEL,
    val showModelSheet: Boolean = false,
    val greeting: String = HardcodedData.greetings.random()
) {
    val canSend: Boolean
        get() = inputText.isNotBlank() && selectedModel.isNotBlank()
}
