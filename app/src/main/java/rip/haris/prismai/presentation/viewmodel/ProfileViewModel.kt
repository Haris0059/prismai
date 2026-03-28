package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rip.haris.prismai.data.model.ProfileState

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun onFullNameChange(value: String) {
        _state.update { it.copy(fullName = value) }
    }

    fun onDisplayNameChange(value: String) {
        _state.update { it.copy(displayName = value) }
    }

    fun onPreferencesChange(value: String) {
        _state.update { it.copy(preferences = value) }
    }

    fun onUpdateProfile() {
        _state.update {
            it.copy(
                savedFullName = it.fullName,
                savedDisplayName = it.displayName
            )
        }
    }

    fun onSavePreferences() {
        _state.update {
            it.copy(savedPreferences = it.preferences)
        }
    }
}
