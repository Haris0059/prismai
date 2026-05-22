package rip.haris.prismai.data.local.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rip.haris.prismai.data.local.dao.AiModelDao
import rip.haris.prismai.data.local.dao.ChatDao
import rip.haris.prismai.data.local.dao.GreetingDao
import rip.haris.prismai.data.local.dao.UserDao
import rip.haris.prismai.data.local.entity.AiModelEntity
import rip.haris.prismai.data.local.entity.ChatEntity
import rip.haris.prismai.data.local.entity.GreetingEntity
import rip.haris.prismai.data.local.entity.UserEntity
import rip.haris.prismai.domain.model.GreetingCategory

class DatabaseSeeder(
    private val scope: CoroutineScope,
    private val userDaoProvider: () -> UserDao,
    private val aiModelDaoProvider: () -> AiModelDao,
    private val greetingDaoProvider: () -> GreetingDao,
    private val chatDaoProvider: () -> ChatDao,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch(Dispatchers.IO) {
            val userDao = userDaoProvider()
            val aiModelDao = aiModelDaoProvider()
            val greetingDao = greetingDaoProvider()
            val chatDao = chatDaoProvider()

            val userId = userDao.insert(
                UserEntity(
                    email = "test@haris.rip",
                    password = "test123",
                    fullName = "Haris Skeledzija",
                    displayName = "Haris",
                    isPro = false,
                    hapticOn = true,
                )
            )

            aiModelDao.insertAll(DefaultSeedData.aiModels)
            greetingDao.insertAll(DefaultSeedData.greetings)

            val defaultModelId = aiModelDao.getByName("Opus 4.6")?.id
            val now = System.currentTimeMillis()
            val chats = DefaultSeedData.sampleChatTitles.mapIndexed { index, title ->
                val offset = (index + 1) * 60_000L
                ChatEntity(
                    userId = userId,
                    modelId = defaultModelId,
                    title = title,
                    createdAt = now - offset,
                    updatedAt = now - offset,
                )
            }
            chatDao.insertAll(chats)
        }
    }
}

object DefaultSeedData {
    val aiModels = listOf(
        AiModelEntity(name = "Opus 4.6", description = "Most capable for ambitious work"),
        AiModelEntity(name = "Sonnet 4.6", description = "Most efficient for everyday tasks"),
        AiModelEntity(name = "GPT 5.4", description = "Advanced reasoning and creativity"),
        AiModelEntity(name = "GPT 5.3 Instant", description = "Fastest for quick answers"),
    )

    val greetings: List<GreetingEntity> = (
        listOf(
            "How can I help you\ntoday?",
            "How can I help you\nthis evening?",
            "What can I help\nwith?",
            "What's on your\nmind?",
            "Ready when\nyou are.",
            "Did you know? \nNo, you didn't!",
        ).map { GreetingEntity(text = it, category = GreetingCategory.NORMAL.name) } +
            listOf(
                "I'm all ears.\nWell, not literally.",
                "Ask me anything.\nI dare you.",
                "Bored? Me too.\nLet's fix that.",
                "No stupid questions.\nBut let's find out.",
                "Thinking cap: on.\nYours too, hopefully.",
                "Go on, impress me.\nI'll wait.",
                "What are we solving\ntoday, genius?",
                "Say the magic word.\n(It's anything, really.)",
                "Plot twist:\nI actually know stuff.",
                "Zero judgment.\nWell, maybe a little.",
                "I've been waiting.\nDon't make it weird.",
                "You had me at\n'Hey'.",
            ).map { GreetingEntity(text = it, category = GreetingCategory.FUNNY.name) } +
            listOf(
                "Let's get to work.\nWhat do you need?",
                "Your ideas matter.\nLet's explore them.",
                "Got a problem?\nLet's solve it together.",
                "Ask away.\nI'm listening.",
                "A new conversation\nawaits.",
                "One question can\nchange everything.",
                "What would you like\nto create today?",
                "Many minds,\none place.",
                "Where curiosity\nbegins.",
                "Think it.\nType it. Done.",
                "Great things start\nwith a single message.",
            ).map { GreetingEntity(text = it, category = GreetingCategory.SERIOUS.name) }
        )

    val sampleChatTitles = listOf(
        "No seriously, why is it working?",
        "Why is my code working?",
        "Best practices for REST API design",
        "Explain recursion like I'm 5",
        "Explain recursion like I'm 3",
        "Is O(n²) really that bad? (it is)",
        "My professor said this is wrong but it runs",
        "Clean architecture in Android with Jetpack",
        "Writing a README nobody will read",
        "How to center a div (still)",
        "How to bypass a SEB",
        "Unit tests or just hope for the best?",
        "My merge conflict has merge conflicts",
        "Ethics in software engineering essay help",
        "Debugging at 2am, a love story",
        "My app works on emulator, not on phone",
        "How to NOT get caught cheating",
        "How to cheat on assignments",
    )
}
