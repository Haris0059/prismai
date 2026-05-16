package rip.haris.prismai.presentation.ui.screens.login

import rip.haris.prismai.presentation.ui.common.LoadStatus

data class LoginUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoggedIn: Boolean = false,
    val status: LoadStatus = LoadStatus.Init,
) {
    val isValid: Boolean
        get() = email.isNotBlank() && emailError == null
}
