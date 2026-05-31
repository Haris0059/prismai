package rip.haris.prismai.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rip.haris.prismai.data.local.dao.AiModelDao
import rip.haris.prismai.data.local.dao.ChatDao
import rip.haris.prismai.data.local.dao.GreetingDao
import rip.haris.prismai.data.local.dao.MessageDao
import rip.haris.prismai.data.local.dao.UserDao
import rip.haris.prismai.data.local.dao.UserPreferenceDao
import rip.haris.prismai.data.local.entity.AiModelEntity
import rip.haris.prismai.data.local.entity.ChatEntity
import rip.haris.prismai.data.local.entity.GreetingEntity
import rip.haris.prismai.data.local.entity.MessageEntity
import rip.haris.prismai.data.local.entity.UserEntity
import rip.haris.prismai.data.local.entity.UserPreferenceEntity

@Database(
    entities = [
        UserEntity::class,
        ChatEntity::class,
        MessageEntity::class,
        AiModelEntity::class,
        UserPreferenceEntity::class,
        GreetingEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class PrismAiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun aiModelDao(): AiModelDao
    abstract fun userPreferenceDao(): UserPreferenceDao
    abstract fun greetingDao(): GreetingDao

    companion object {
        const val DB_NAME = "prismai.db"
    }
}
