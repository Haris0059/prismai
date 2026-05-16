package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.AiModel

interface AiModelRepository {
    fun observeAll(): Flow<List<AiModel>>
    suspend fun getById(id: Long): AiModel?
    suspend fun getByName(name: String): AiModel?
    suspend fun insert(model: AiModel): Long
    suspend fun update(model: AiModel)
    suspend fun delete(model: AiModel)
}
