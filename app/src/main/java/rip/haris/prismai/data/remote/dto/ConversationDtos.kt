package rip.haris.prismai.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** One row from `GET /conversations`. */
@Serializable
data class ConversationDto(
    val id: String,
    val title: String,
    val provider: String,
    val model: String,
    @SerialName("updated_at") val updatedAt: String,
)

/** A single message inside a conversation detail response. */
@Serializable
data class MessageDto(
    val role: String,
    val content: String,
    @SerialName("created_at") val createdAt: String,
)

/** `GET /conversations/{id}`. */
@Serializable
data class ConversationDetailDto(
    val id: String,
    val title: String,
    val provider: String,
    val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val messages: List<MessageDto>,
)

/** Body for `PUT /conversations/{id}`. */
@Serializable
data class RenameRequestDto(
    val title: String,
)

/** Response from the rename endpoint. */
@Serializable
data class RenameResponseDto(
    val id: String,
    val title: String,
)

/** Response from `DELETE /conversations/{id}`. */
@Serializable
data class DeleteResponseDto(
    val deleted: Boolean,
)

/** Body for `POST /chat/{provider}`. */
@Serializable
data class ChatRequestDto(
    val model: String,
    val message: String,
    @SerialName("conversation_id") val conversationId: String? = null,
)
