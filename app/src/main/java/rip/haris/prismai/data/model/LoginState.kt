package rip.haris.prismai.data.model

data class LoginState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false
)
