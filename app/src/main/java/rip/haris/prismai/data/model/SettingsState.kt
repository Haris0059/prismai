package rip.haris.prismai.data.model

data class SettingsState(
    val userEmail: String = HardcodedData.DEFAULT_USER_EMAIL,
    val isPro: Boolean = true,
    val hapticFeedbackEnabled: Boolean = true
)
