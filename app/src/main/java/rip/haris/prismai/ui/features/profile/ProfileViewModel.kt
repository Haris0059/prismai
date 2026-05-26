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
import rip.haris.prismai.domain.model.UserPreference
import rip.haris.prismai.domain.repository.UserPreferenceRepository
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.ui.common.LoadStatus
import rip.haris.prismai.ui.features.profile.ProfileUiState

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: UserPreferenceRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    companion object {
        private const val PREF_KEY = "freeform"
    }

    init {
        userRepository.observeCurrentUser()
            .onEach { user ->
                if (user == null) {
                    _uiState.update { it.copy(status = LoadStatus.Error("No user")) }
                    return@onEach
                }
                val savedPref = preferenceRepository.getByKey(user.id, PREF_KEY)?.value.orEmpty()
                _uiState.update {
                    it.copy(
                        fullName = user.fullName,
                        displayName = user.displayName,
                        savedFullName = user.fullName,
                        savedDisplayName = user.displayName,
                        preferences = savedPref,
                        savedPreferences = savedPref,
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
            val existing = preferenceRepository.getByKey(current.id, PREF_KEY)
            val updated = existing?.copy(value = state.preferences)
                ?: UserPreference(userId = current.id, key = PREF_KEY, value = state.preferences)
            if (existing == null) preferenceRepository.insert(updated)
            else preferenceRepository.update(updated)
            _uiState.update { it.copy(savedPreferences = state.preferences) }
        }
    }
}
