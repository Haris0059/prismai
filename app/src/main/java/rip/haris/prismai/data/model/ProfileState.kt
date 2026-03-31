package rip.haris.prismai.data.model

data class ProfileState(
    val fullName: String = "Haris Skeledzija",
    val displayName: String = "Haris",
    val preferences: String = "",
    val savedFullName: String = "Haris Skeledzija",
    val savedDisplayName: String = "Haris",
    val savedPreferences: String = ""
) {
    val isProfileChanged: Boolean
        get() = fullName != savedFullName || displayName != savedDisplayName

    val isPreferencesChanged: Boolean
        get() = preferences != savedPreferences
}
