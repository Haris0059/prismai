package rip.haris.prismai.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rip.haris.prismai.data.local.dao.UserDao
import rip.haris.prismai.data.mapper.toDomain
import rip.haris.prismai.data.mapper.toEntity
import rip.haris.prismai.domain.model.User
import rip.haris.prismai.domain.repository.UserRepository

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao,
) : UserRepository {
    override fun observeCurrentUser(): Flow<User?> =
        dao.observeCurrentUser().map { it?.toDomain() }

    override suspend fun getById(id: Long): User? = dao.getById(id)?.toDomain()

    override suspend fun getByEmail(email: String): User? = dao.getByEmail(email)?.toDomain()

    override suspend fun insert(user: User): Long = dao.insert(user.toEntity())

    override suspend fun update(user: User) = dao.update(user.toEntity())

    override suspend fun delete(user: User) = dao.delete(user.toEntity())
}
