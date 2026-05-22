package rip.haris.prismai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.data.local.entity.AiModelEntity

@Dao
interface AiModelDao {
    @Query("SELECT * FROM ai_models ORDER BY id ASC")
    fun observeAll(): Flow<List<AiModelEntity>>

    @Query("SELECT * FROM ai_models WHERE id = :id")
    suspend fun getById(id: Long): AiModelEntity?

    @Query("SELECT * FROM ai_models WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): AiModelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: AiModelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(models: List<AiModelEntity>)

    @Update
    suspend fun update(model: AiModelEntity)

    @Delete
    suspend fun delete(model: AiModelEntity)
}
