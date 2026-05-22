package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.MessageDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.Message
import rip.haris.prismai.domain.repository.MessageRepository

class MessageRepositoryImpl @Inject constructor(
    private val dao: MessageDao,
) : MessageRepository {
    override fun observeMessages(chatId: Long): Flow<List<Message>> =
        dao.observeMessages(chatId).map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: Long): Message? = dao.getById(id)?.toDomain()

    override suspend fun insert(message: Message): Long = dao.insert(message.toEntity())

    override suspend fun update(message: Message) = dao.update(message.toEntity())

    override suspend fun delete(message: Message) = dao.delete(message.toEntity())

    override suspend fun deleteByChatId(chatId: Long) = dao.deleteByChatId(chatId)
}
