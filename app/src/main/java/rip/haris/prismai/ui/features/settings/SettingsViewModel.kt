package rip.haris.prismai.ui.features.settings

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
import rip.haris.prismai.ui.features.settings.SettingsUiState

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(status = LoadStatus.Loading))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        userRepository.observeCurrentUser()
            .onEach { user ->
                if (user == null) {
                    _uiState.update { it.copy(status = LoadStatus.Error("No user")) }
                } else {
                    _uiState.update {
                        it.copy(
                            userEmail = user.email,
                            isPro = user.isPro,
                            hapticFeedbackEnabled = user.hapticOn,
                            status = LoadStatus.Success,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onHapticFeedbackToggle(enabled: Boolean) {
        _uiState.update { it.copy(hapticFeedbackEnabled = enabled) }
        viewModelScope.launch {
            val current = userRepository.observeCurrentUser().first { it != null } ?: return@launch
            userRepository.update(current.copy(hapticOn = enabled))
        }
    }
}
