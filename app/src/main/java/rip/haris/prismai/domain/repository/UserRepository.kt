package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.User

interface UserRepository {
    fun observeCurrentUser(): Flow<User?>
    suspend fun getById(id: Long): User?
    suspend fun getByEmail(email: String): User?
    suspend fun insert(user: User): Long
    suspend fun update(user: User)
    suspend fun delete(user: User)
}
