package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.Message

interface MessageRepository {
    fun observeMessages(chatId: Long): Flow<List<Message>>
    suspend fun getById(id: Long): Message?
    suspend fun insert(message: Message): Long
    suspend fun update(message: Message)
    suspend fun delete(message: Message)
    suspend fun deleteByChatId(chatId: Long)
}
