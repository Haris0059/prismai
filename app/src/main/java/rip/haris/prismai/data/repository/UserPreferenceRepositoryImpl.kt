package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.UserPreferenceDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.UserPreference
import rip.haris.prismai.domain.repository.UserPreferenceRepository

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dao: UserPreferenceDao,
) : UserPreferenceRepository {
    override fun observeForUser(userId: Long): Flow<List<UserPreference>> =
        dao.observeForUser(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun getByKey(userId: Long, key: String): UserPreference? =
        dao.getByKey(userId, key)?.toDomain()

    override suspend fun insert(preference: UserPreference): Long =
        dao.insert(preference.toEntity())

    override suspend fun update(preference: UserPreference) = dao.update(preference.toEntity())

    override suspend fun delete(preference: UserPreference) = dao.delete(preference.toEntity())

    override suspend fun deleteAllForUser(userId: Long) = dao.deleteAllForUser(userId)
}
