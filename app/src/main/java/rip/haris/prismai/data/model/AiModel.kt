package rip.haris.prismai.data.model

data class AiModel(
    val name: String,
    val description: String
)

val availableModels = listOf(
    AiModel("Opus 4.6", "Most capable for ambitious work"),
    AiModel("Sonnet 4.6", "Most efficient for everyday tasks"),
    AiModel("GPT 5.4", "Advanced reasoning and creativity"),
    AiModel("GPT 5.3 Instant", "Fastest for quick answers")
)
