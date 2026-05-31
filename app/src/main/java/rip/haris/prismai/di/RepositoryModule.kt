package rip.haris.prismai.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import rip.haris.prismai.data.repository.AiModelRepositoryImpl
import rip.haris.prismai.data.repository.ChatRepositoryImpl
import rip.haris.prismai.data.repository.ConversationRepositoryImpl
import rip.haris.prismai.data.repository.GreetingRepositoryImpl
import rip.haris.prismai.data.repository.MessageRepositoryImpl
import rip.haris.prismai.data.repository.UserPreferenceRepositoryImpl
import rip.haris.prismai.data.repository.UserRepositoryImpl
import rip.haris.prismai.domain.repository.AiModelRepository
import rip.haris.prismai.domain.repository.ChatRepository
import rip.haris.prismai.domain.repository.ConversationRepository
import rip.haris.prismai.domain.repository.GreetingRepository
import rip.haris.prismai.domain.repository.MessageRepository
import rip.haris.prismai.domain.repository.UserPreferenceRepository
import rip.haris.prismai.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun bindConversationRepository(impl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    @Singleton
    abstract fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository

    @Binds
    @Singleton
    abstract fun bindAiModelRepository(impl: AiModelRepositoryImpl): AiModelRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferenceRepository(impl: UserPreferenceRepositoryImpl): UserPreferenceRepository

    @Binds
    @Singleton
    abstract fun bindGreetingRepository(impl: GreetingRepositoryImpl): GreetingRepository
}
