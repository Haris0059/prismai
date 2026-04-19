package rip.haris.prismai.data.model

val greetings = listOf(
    // Normal
    "How can I help you\ntoday?",
    "How can I help you\nthis evening?",
    "What can I help\nwith?",
    "What's on your\nmind?",
    "Ready when\nyou are.",
    "Did you know? \nNo, you didn't!",

    // Funny
    "I'm all ears.\nWell, not literally.",
    "Ask me anything.\nI dare you.",
    "Bored? Me too.\nLet's fix that.",
    "No stupid questions.\nBut let's find out.",
    "Thinking cap: on.\nYours too, hopefully.",
    "Go on, impress me.\nI'll wait.",
    "What are we solving\ntoday, genius?",
    "Say the magic word.\n(It's anything, really.)",
    "Plot twist:\nI actually know stuff.",
    "Zero judgment.\nWell, maybe a little.",
    "I've been waiting.\nDon't make it weird.",
    "You had me at\n'Hey'.",

    // Serious / Calm
    "Let's get to work.\nWhat do you need?",
    "Your ideas matter.\nLet's explore them.",
    "Got a problem?\nLet's solve it together.",
    "Ask away.\nI'm listening.",
    "A new conversation\nawaits.",
    "One question can\nchange everything.",
    "What would you like\nto create today?",
    "Many minds,\none place.",
    "Where curiosity\nbegins.",
    "Think it.\nType it. Done.",
    "Great things start\nwith a single message.",
)

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val selectedModel: String = "Opus 4.6",
    val showModelSheet: Boolean = false,
    val greeting: String = greetings.random()
) {
    val canSend: Boolean
        get() = inputText.isNotBlank() && selectedModel.isNotBlank()
}
