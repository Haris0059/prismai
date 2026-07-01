package rip.haris.prismai.data.repository

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import rip.haris.prismai.data.remote.api.PrismAiApi
import rip.haris.prismai.data.remote.dto.ChatRequestDto
import rip.haris.prismai.data.remote.dto.ConversationDto
import rip.haris.prismai.data.remote.dto.RenameRequestDto
import rip.haris.prismai.domain.model.ChatReply
import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.domain.repository.ConversationRepository

class ConversationRepositoryImpl @Inject constructor(
    private val api: PrismAiApi,
    private val json: Json,
) : ConversationRepository {

    override suspend fun getConversations(): List<Conversation> =
        api.getConversations().map { it.toDomain() }

    override suspend fun getMessages(conversationId: String): List<Message> =
        api.getConversation(conversationId).messages.map { dto ->
            Message(
                chatId = 0,
                text = dto.content,
                isUser = dto.role == "user",
                createdAt = parseIsoToMillis(dto.createdAt),
            )
        }

    override suspend fun rename(id: String, title: String) {
        api.renameConversation(id, RenameRequestDto(title))
    }

    override suspend fun delete(id: String) {
        api.deleteConversation(id)
    }

    override suspend fun sendMessage(
        provider: String,
        model: String,
        message: String,
        conversationId: String?,
    ): ChatReply = withContext(Dispatchers.IO) {
        val body = api.chat(provider, ChatRequestDto(model, message, conversationId))
        var convId = conversationId.orEmpty()
        val text = StringBuilder()

        body.use { responseBody ->
            val source = responseBody.source()
            while (true) {
                val line = source.readUtf8Line() ?: break
                if (!line.startsWith("data:")) continue
                val payload = line.removePrefix("data:").trim()
                if (payload.isEmpty()) continue
                if (payload == "[DONE]") break

                val obj = runCatching { json.parseToJsonElement(payload).jsonObject }.getOrNull() ?: continue
                obj["conversation_id"]?.jsonPrimitive?.content?.let { convId = it }
                obj["text"]?.jsonPrimitive?.content?.let { text.append(it) }
                obj["error"]?.jsonPrimitive?.content?.let { error ->
                    throw IllegalStateException(error)
                }
            }
        }

        ChatReply(conversationId = convId, text = text.toString())
    }

    private fun ConversationDto.toDomain() = Conversation(
        id = id,
        title = title,
        provider = provider,
        model = model,
        updatedAtMillis = parseIsoToMillis(updatedAt),
    )

    private fun parseIsoToMillis(iso: String): Long = runCatching {
        // Best-effort parse of the "yyyy-MM-ddTHH:mm:ss" prefix as UTC; ignores
        // fractional seconds / offset. Sufficient for "time ago" display on minSdk 24.
        val prefix = iso.take(19)
        val fmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        fmt.parse(prefix)?.time ?: System.currentTimeMillis()
    }.getOrDefault(System.currentTimeMillis())
}
