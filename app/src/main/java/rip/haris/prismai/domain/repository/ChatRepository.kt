package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.Chat

interface ChatRepository {
    fun observeChats(userId: Long): Flow<List<Chat>>
    fun observeById(id: Long): Flow<Chat?>
    suspend fun getById(id: Long): Chat?
    suspend fun createChat(chat: Chat): Long
    suspend fun update(chat: Chat)
    suspend fun touch(id: Long, timestamp: Long)
    suspend fun delete(chat: Chat)
    suspend fun deleteById(id: Long)
}
