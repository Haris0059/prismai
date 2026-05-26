package rip.haris.prismai.ui.features.profile

import rip.haris.prismai.ui.common.LoadStatus

data class ProfileUiState(
    val fullName: String = "",
    val displayName: String = "",
    val preferences: String = "",
    val savedFullName: String = "",
    val savedDisplayName: String = "",
    val savedPreferences: String = "",
    val status: LoadStatus = LoadStatus.Init,
) {
    val isProfileChanged: Boolean
        get() = fullName != savedFullName || displayName != savedDisplayName

    val isPreferencesChanged: Boolean
        get() = preferences != savedPreferences
}
