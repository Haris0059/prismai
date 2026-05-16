package rip.haris.prismai.presentation.ui.screens.settings

import rip.haris.prismai.presentation.ui.common.LoadStatus

data class SettingsUiState(
    val userEmail: String = "",
    val isPro: Boolean = false,
    val hapticFeedbackEnabled: Boolean = true,
    val status: LoadStatus = LoadStatus.Init,
)
