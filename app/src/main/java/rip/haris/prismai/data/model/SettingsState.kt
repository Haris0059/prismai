package rip.haris.prismai.data.model

data class SettingsState(
    val userEmail: String = "test@haris.rip",
    val isPro: Boolean = true,
    val hapticFeedbackEnabled: Boolean = true
)
