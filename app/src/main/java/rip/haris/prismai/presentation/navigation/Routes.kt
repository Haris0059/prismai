package rip.haris.prismai.presentation.navigation

import android.net.Uri

object Routes {
    const val LOGIN = "login"
    const val CHAT = "chat"
    const val CHAT_HISTORY = "chatHistory"
    const val SETTINGS = "settings"
    const val PROFILE = "profile"

    const val ARG_CHAT_ID = "chatId"
    const val ARG_TITLE = "title"

    const val CHAT_DETAIL = "chat/{$ARG_CHAT_ID}?$ARG_TITLE={$ARG_TITLE}"

    fun chatDetail(chatId: String, title: String): String =
        "chat/$chatId?$ARG_TITLE=${Uri.encode(title)}"
}
