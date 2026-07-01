package rip.haris.prismai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.data.local.entity.GreetingEntity

@Dao
interface GreetingDao {
    @Query("SELECT * FROM greetings")
    fun observeAll(): Flow<List<GreetingEntity>>

    @Query("SELECT COUNT(*) FROM greetings")
    suspend fun count(): Int

    @Query("SELECT * FROM greetings ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandom(): GreetingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(greeting: GreetingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(greetings: List<GreetingEntity>)

    @Update
    suspend fun update(greeting: GreetingEntity)

    @Delete
    suspend fun delete(greeting: GreetingEntity)
}
