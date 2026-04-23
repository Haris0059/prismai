package rip.haris.prismai.presentation.navigation

import rip.haris.prismai.presentation.viewmodel.ChatHistoryViewModel
import rip.haris.prismai.presentation.viewmodel.ChatViewModel
import rip.haris.prismai.presentation.viewmodel.LoginViewModel
import rip.haris.prismai.presentation.viewmodel.ProfileViewModel
import rip.haris.prismai.presentation.viewmodel.SettingsViewModel

data class AppViewModels(
    val login: LoginViewModel,
    val chat: ChatViewModel,
    val chatHistory: ChatHistoryViewModel,
    val settings: SettingsViewModel,
    val profile: ProfileViewModel,
)
