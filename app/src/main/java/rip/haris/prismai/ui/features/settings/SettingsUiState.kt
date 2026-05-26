package rip.haris.prismai.ui.features.settings

import rip.haris.prismai.ui.common.LoadStatus

data class SettingsUiState(
    val userEmail: String = "",
    val isPro: Boolean = false,
    val hapticFeedbackEnabled: Boolean = true,
    val status: LoadStatus = LoadStatus.Init,
)
