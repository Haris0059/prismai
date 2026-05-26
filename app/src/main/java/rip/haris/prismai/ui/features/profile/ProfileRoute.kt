package rip.haris.prismai.ui.features.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rip.haris.prismai.ui.features.profile.ProfileViewModel

@Composable
fun ProfileRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    ProfileScreen(
        uiState = uiState,
        onFullNameChange = viewModel::onFullNameChange,
        onDisplayNameChange = viewModel::onDisplayNameChange,
        onPreferencesChange = viewModel::onPreferencesChange,
        onUpdateProfile = viewModel::onUpdateProfile,
        onSavePreferences = viewModel::onSavePreferences,
        onBack = onBack,
        modifier = modifier,
    )
}
