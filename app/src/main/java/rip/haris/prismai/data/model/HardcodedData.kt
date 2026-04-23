package rip.haris.prismai.data.model

object HardcodedData {

    const val DEFAULT_USER_EMAIL: String = "test@haris.rip"

    const val DEFAULT_SELECTED_MODEL: String = "Opus 4.6"

    const val SIMULATED_AI_RESPONSE: String =
        "This is a simulated AI response. In a real app, this would come from an API."

    val greetings: List<String> = listOf(
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

    val suggestedPrompts: List<String> = listOf(
        "Explain recursion simply",
        "Summarize this article",
        "Write a unit test",
        "Debug my code",
        "Plan my week",
        "Brainstorm project names",
        "Draft an email",
        "Review my resume",
    )

    val availableModels: List<AiModel> = listOf(
        AiModel("Opus 4.6", "Most capable for ambitious work"),
        AiModel("Sonnet 4.6", "Most efficient for everyday tasks"),
        AiModel("GPT 5.4", "Advanced reasoning and creativity"),
        AiModel("GPT 5.3 Instant", "Fastest for quick answers"),
    )

    val sampleChats: List<ChatHistoryItem> = listOf(
        ChatHistoryItem("chat-01", "No seriously, why is it working?", "2 minutes ago"),
        ChatHistoryItem("chat-02", "Why is my code working?", "8 minutes ago"),
        ChatHistoryItem("chat-03", "Best practices for REST API design", "1 hour ago"),
        ChatHistoryItem("chat-04", "Explain recursion like I'm 5", "2 hours ago"),
        ChatHistoryItem("chat-05", "Explain recursion like I'm 3", "2 hours ago"),
        ChatHistoryItem("chat-06", "Is O(n²) really that bad? (it is)", "5 hours ago"),
        ChatHistoryItem("chat-07", "My professor said this is wrong but it runs", "7 hours ago"),
        ChatHistoryItem("chat-08", "Clean architecture in Android with Jetpack", "8 hours ago"),
        ChatHistoryItem("chat-09", "Writing a README nobody will read", "9 hours ago"),
        ChatHistoryItem("chat-10", "How to center a div (still)", "10 hours ago"),
        ChatHistoryItem("chat-11", "How to bypass a SEB", "12 hours ago"),
        ChatHistoryItem("chat-12", "Unit tests or just hope for the best?", "Yesterday"),
        ChatHistoryItem("chat-13", "My merge conflict has merge conflicts", "2 days ago"),
        ChatHistoryItem("chat-14", "Ethics in software engineering essay help", "1 week ago"),
        ChatHistoryItem("chat-15", "Debugging at 2am, a love story", "1 week ago"),
        ChatHistoryItem("chat-16", "My app works on emulator, not on phone", "1 week ago"),
        ChatHistoryItem("chat-17", "How to NOT get caught cheating", "1 week ago"),
        ChatHistoryItem("chat-18", "How to cheat on assignments", "2 week ago"),
    )
}
