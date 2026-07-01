package rip.haris.prismai.data.mapper

import rip.haris.prismai.data.local.entity.AiModelEntity
import rip.haris.prismai.data.local.entity.ChatEntity
import rip.haris.prismai.data.local.entity.GreetingEntity
import rip.haris.prismai.data.local.entity.MessageEntity
import rip.haris.prismai.data.local.entity.UserEntity
import rip.haris.prismai.data.local.entity.UserPreferenceEntity
import rip.haris.prismai.domain.model.AiModel
import rip.haris.prismai.domain.model.Chat
import rip.haris.prismai.domain.model.Greeting
import rip.haris.prismai.domain.model.GreetingCategory
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.domain.model.User
import rip.haris.prismai.domain.model.UserPreference

fun UserEntity.toDomain() = User(id, email, fullName, displayName, isPro, hapticOn)
fun User.toEntity() = UserEntity(id, email, fullName, displayName, isPro, hapticOn)

fun AiModelEntity.toDomain() = AiModel(id, name, description)
fun AiModel.toEntity() = AiModelEntity(id, name, description)

fun ChatEntity.toDomain() = Chat(id, userId, modelId, title, createdAt, updatedAt)
fun Chat.toEntity() = ChatEntity(id, userId, modelId, title, createdAt, updatedAt)

fun MessageEntity.toDomain() = Message(id, chatId, text, isUser, createdAt)
fun Message.toEntity() = MessageEntity(id, chatId, text, isUser, createdAt)

fun UserPreferenceEntity.toDomain() = UserPreference(id, userId, key, value)
fun UserPreference.toEntity() = UserPreferenceEntity(id, userId, key, value)

fun GreetingEntity.toDomain() = Greeting(
    id = id,
    text = text,
    category = runCatching { GreetingCategory.valueOf(category) }.getOrDefault(GreetingCategory.NORMAL),
)

fun Greeting.toEntity() = GreetingEntity(id, text, category.name)
