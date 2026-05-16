package rip.haris.prismai.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import rip.haris.prismai.data.local.dao.AiModelDao
import rip.haris.prismai.data.local.dao.ChatDao
import rip.haris.prismai.data.local.dao.GreetingDao
import rip.haris.prismai.data.local.dao.MessageDao
import rip.haris.prismai.data.local.dao.UserDao
import rip.haris.prismai.data.local.dao.UserPreferenceDao
import rip.haris.prismai.data.local.db.PrismAiDatabase
import rip.haris.prismai.data.local.util.DatabaseSeeder

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PrismAiDatabase {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        lateinit var database: PrismAiDatabase
        database = Room.databaseBuilder(
            context,
            PrismAiDatabase::class.java,
            PrismAiDatabase.DB_NAME,
        )
            .addCallback(
                DatabaseSeeder(
                    scope = scope,
                    userDaoProvider = { database.userDao() },
                    aiModelDaoProvider = { database.aiModelDao() },
                    greetingDaoProvider = { database.greetingDao() },
                    chatDaoProvider = { database.chatDao() },
                )
            )
            .fallbackToDestructiveMigration()
            .build()
        return database
    }

    @Provides fun provideUserDao(db: PrismAiDatabase): UserDao = db.userDao()
    @Provides fun provideChatDao(db: PrismAiDatabase): ChatDao = db.chatDao()
    @Provides fun provideMessageDao(db: PrismAiDatabase): MessageDao = db.messageDao()
    @Provides fun provideAiModelDao(db: PrismAiDatabase): AiModelDao = db.aiModelDao()
    @Provides fun provideUserPreferenceDao(db: PrismAiDatabase): UserPreferenceDao = db.userPreferenceDao()
    @Provides fun provideGreetingDao(db: PrismAiDatabase): GreetingDao = db.greetingDao()
}
