package rip.haris.prismai.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.ui.common.LoadStatus

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        userRepository.observeCurrentUser()
            .onEach { user ->
                if (user == null) {
                    _uiState.update { it.copy(status = LoadStatus.Error("No user")) }
                    return@onEach
                }
                _uiState.update {
                    it.copy(
                        fullName = user.fullName,
                        displayName = user.displayName,
                        savedFullName = user.fullName,
                        savedDisplayName = user.displayName,
                        preferences = user.preferences,
                        savedPreferences = user.preferences,
                        status = LoadStatus.Success,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onFullNameChange(value: String) {
        _uiState.update { it.copy(fullName = value) }
    }

    fun onDisplayNameChange(value: String) {
        _uiState.update { it.copy(displayName = value) }
    }

    fun onPreferencesChange(value: String) {
        _uiState.update { it.copy(preferences = value) }
    }

    fun onUpdateProfile() {
        viewModelScope.launch {
            val current = userRepository.observeCurrentUser().first { it != null } ?: return@launch
            val state = _uiState.value
            userRepository.update(
                current.copy(fullName = state.fullName, displayName = state.displayName)
            )
            _uiState.update {
                it.copy(savedFullName = state.fullName, savedDisplayName = state.displayName)
            }
        }
    }

    fun onSavePreferences() {
        viewModelScope.launch {
            val current = userRepository.observeCurrentUser().first { it != null } ?: return@launch
            val state = _uiState.value
            userRepository.update(current.copy(preferences = state.preferences))
            _uiState.update { it.copy(savedPreferences = state.preferences) }
        }
    }
}
