package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.GreetingDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.Greeting
import rip.haris.prismai.domain.repository.GreetingRepository

class GreetingRepositoryImpl @Inject constructor(
    private val dao: GreetingDao,
) : GreetingRepository {
    override fun observeAll(): Flow<List<Greeting>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getRandom(): Greeting? = dao.getRandom()?.toDomain()

    override suspend fun insert(greeting: Greeting): Long = dao.insert(greeting.toEntity())

    override suspend fun update(greeting: Greeting) = dao.update(greeting.toEntity())

    override suspend fun delete(greeting: Greeting) = dao.delete(greeting.toEntity())
}
