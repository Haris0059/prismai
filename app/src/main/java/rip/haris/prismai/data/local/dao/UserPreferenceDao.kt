package rip.haris.prismai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.data.local.entity.UserPreferenceEntity

@Dao
interface UserPreferenceDao {
    @Query("SELECT * FROM user_preferences WHERE userId = :userId ORDER BY id ASC")
    fun observeForUser(userId: Long): Flow<List<UserPreferenceEntity>>

    @Query("SELECT * FROM user_preferences WHERE userId = :userId AND `key` = :key LIMIT 1")
    suspend fun getByKey(userId: Long, key: String): UserPreferenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: UserPreferenceEntity): Long

    @Update
    suspend fun update(preference: UserPreferenceEntity)

    @Delete
    suspend fun delete(preference: UserPreferenceEntity)

    @Query("DELETE FROM user_preferences WHERE userId = :userId")
    suspend fun deleteAllForUser(userId: Long)
}
