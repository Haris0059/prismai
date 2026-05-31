package rip.haris.prismai.domain.repository

import rip.haris.prismai.domain.model.ChatReply
import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.domain.model.Message

/**
 * Network-backed access to the PrismAI backend conversations. Implemented over Retrofit.
 * All methods talk to the REST API directly (no local cache for this assignment).
 */
interface ConversationRepository {
    suspend fun getConversations(): List<Conversation>
    suspend fun getMessages(conversationId: String): List<Message>
    suspend fun rename(id: String, title: String)
    suspend fun delete(id: String)
    suspend fun sendMessage(provider: String, model: String, message: String, conversationId: String?): ChatReply
}
