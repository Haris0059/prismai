package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rip.haris.prismai.data.model.LoginState

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = null
            )
        }
    }

    fun onEmailSubmit() {
        val email = _state.value.email.trim()

        if (email.isBlank()) {
            _state.update { it.copy(emailError = "Email cannot be empty") }
            return
        }

        if (!isValidEmail(email)) {
            _state.update { it.copy(emailError = "Please enter a valid email address") }
            return
        }

        if (email != VALID_EMAIL) {
            _state.update { it.copy(emailError = "Account not found") }
            return
        }

        _state.update { it.copy(emailError = null, isLoggedIn = true) }
    }

    companion object {
        private const val VALID_EMAIL = "test@haris.rip"
    }

    fun onGoogleSignIn() {
        _state.update { it.copy(isLoading = true) }
    }

    fun onLogout() {
        _state.update { LoginState() }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
