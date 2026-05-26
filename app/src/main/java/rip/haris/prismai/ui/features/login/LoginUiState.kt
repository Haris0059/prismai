package rip.haris.prismai.ui.features.login

import rip.haris.prismai.ui.common.LoadStatus

data class LoginUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoggedIn: Boolean = false,
    val status: LoadStatus = LoadStatus.Init,
) {
    val isValid: Boolean
        get() = email.isNotBlank() && emailError == null
}
