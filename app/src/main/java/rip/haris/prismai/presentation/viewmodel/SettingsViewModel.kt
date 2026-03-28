package rip.haris.prismai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rip.haris.prismai.data.model.SettingsState

class SettingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun onHapticFeedbackToggle(enabled: Boolean) {
        _state.update { it.copy(hapticFeedbackEnabled = enabled) }
    }
}
