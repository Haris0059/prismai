package rip.haris.prismai.ui.features.login

import rip.haris.prismai.ui.common.LoadStatus

enum class AuthMode { SIGN_IN, SIGN_UP }

data class LoginUiState(
    val mode: AuthMode = AuthMode.SIGN_IN,
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoggedIn: Boolean = false,
    val status: LoadStatus = LoadStatus.Init,
) {
    val isSubmitting: Boolean get() = status is LoadStatus.Loading

    val isValid: Boolean
        get() = email.isNotBlank() && password.isNotBlank() &&
            emailError == null && passwordError == null
}
