package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.Greeting

interface GreetingRepository {
    fun observeAll(): Flow<List<Greeting>>
    suspend fun getRandom(): Greeting?
    suspend fun insert(greeting: Greeting): Long
    suspend fun update(greeting: Greeting)
    suspend fun delete(greeting: Greeting)
}
