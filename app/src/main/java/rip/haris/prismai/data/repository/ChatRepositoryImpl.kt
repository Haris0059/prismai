package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.ChatDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.Chat
import rip.haris.prismai.domain.repository.ChatRepository

class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatDao,
) : ChatRepository {
    override fun observeChats(userId: Long): Flow<List<Chat>> =
        dao.observeChats(userId).map { list -> list.map { it.toDomain() } }

    override fun observeById(id: Long): Flow<Chat?> =
        dao.observeById(id).map { it?.toDomain() }

    override suspend fun getById(id: Long): Chat? = dao.getById(id)?.toDomain()

    override suspend fun createChat(chat: Chat): Long = dao.insert(chat.toEntity())

    override suspend fun update(chat: Chat) = dao.update(chat.toEntity())

    override suspend fun touch(id: Long, timestamp: Long) = dao.touch(id, timestamp)

    override suspend fun delete(chat: Chat) = dao.delete(chat.toEntity())

    override suspend fun deleteById(id: Long) = dao.deleteById(id)
}
