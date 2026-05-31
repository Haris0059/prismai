package rip.haris.prismai.domain.model

/**
 * A conversation as stored on the backend. Distinct from the local [Chat] (which is
 * Room-backed with a Long id) — this one carries the server's UUID string id and is
 * fetched over the network.
 */
data class Conversation(
    val id: String,
    val title: String,
    val provider: String,
    val model: String,
    val updatedAtMillis: Long,
)

/** Result of a `POST /chat` send: the server conversation id plus the assistant's reply. */
data class ChatReply(
    val conversationId: String,
    val text: String,
)
