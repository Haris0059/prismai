package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.UserPreference

interface UserPreferenceRepository {
    fun observeForUser(userId: Long): Flow<List<UserPreference>>
    suspend fun getByKey(userId: Long, key: String): UserPreference?
    suspend fun insert(preference: UserPreference): Long
    suspend fun update(preference: UserPreference)
    suspend fun delete(preference: UserPreference)
    suspend fun deleteAllForUser(userId: Long)
}
