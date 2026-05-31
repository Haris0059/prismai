package rip.haris.prismai.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rip.haris.prismai.data.session.SessionManager
import rip.haris.prismai.ui.common.LoadStatus

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null, status = LoadStatus.Init) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null, status = LoadStatus.Init) }
    }

    fun onToggleMode() {
        _uiState.update {
            it.copy(
                mode = if (it.mode == AuthMode.SIGN_IN) AuthMode.SIGN_UP else AuthMode.SIGN_IN,
                emailError = null,
                passwordError = null,
                status = LoadStatus.Init,
            )
        }
    }

    fun onSubmit() {
        val state = _uiState.value
        val email = state.email.trim()
        val password = state.password

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(emailError = "Please enter a valid email address") }
            return
        }
        if (password.length < 6) {
            _uiState.update { it.copy(passwordError = "Password must be at least 6 characters") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(status = LoadStatus.Loading) }
            val result = when (state.mode) {
                AuthMode.SIGN_IN -> sessionManager.signIn(email, password)
                AuthMode.SIGN_UP -> sessionManager.signUp(email, password, displayName = email.substringBefore("@"))
            }
            result
                .onSuccess {
                    _uiState.update { it.copy(isLoggedIn = true, status = LoadStatus.Success) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(status = LoadStatus.Error(authErrorMessage(e))) }
                }
        }
    }

    private fun authErrorMessage(e: Throwable): String = when (e) {
        is FirebaseAuthWeakPasswordException -> "Password is too weak"
        is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
        is FirebaseAuthInvalidUserException -> "No account found for this email"
        is FirebaseAuthUserCollisionException -> "An account already exists for this email"
        else -> e.message ?: "Authentication failed"
    }
}
