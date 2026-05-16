package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.presentation.ui.common.LoadStatus
import rip.haris.prismai.presentation.ui.screens.login.LoginUiState

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onEmailSubmit() {
        val email = _uiState.value.email.trim()
        if (email.isBlank()) {
            _uiState.update { it.copy(emailError = "Email cannot be empty") }
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(emailError = "Please enter a valid email address") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(status = LoadStatus.Loading) }
            runCatching { userRepository.getByEmail(email) }
                .onSuccess { user ->
                    if (user == null) {
                        _uiState.update {
                            it.copy(emailError = "Account not found", status = LoadStatus.Error("Account not found"))
                        }
                    } else {
                        _uiState.update {
                            it.copy(emailError = null, isLoggedIn = true, status = LoadStatus.Success)
                        }
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(status = LoadStatus.Error(e.message ?: "Login failed")) }
                }
        }
    }

    fun onGoogleSignIn() {
        _uiState.update { it.copy(status = LoadStatus.Loading) }
    }

    fun onLogout() {
        _uiState.update { LoginUiState() }
    }
}
