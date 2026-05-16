package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.AiModelDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.AiModel
import rip.haris.prismai.domain.repository.AiModelRepository

class AiModelRepositoryImpl @Inject constructor(
    private val dao: AiModelDao,
) : AiModelRepository {
    override fun observeAll(): Flow<List<AiModel>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getById(id: Long): AiModel? = dao.getById(id)?.toDomain()

    override suspend fun getByName(name: String): AiModel? = dao.getByName(name)?.toDomain()

    override suspend fun insert(model: AiModel): Long = dao.insert(model.toEntity())

    override suspend fun update(model: AiModel) = dao.update(model.toEntity())

    override suspend fun delete(model: AiModel) = dao.delete(model.toEntity())
}
