package rip.haris.prismai.presentation.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rip.haris.prismai.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsRoute(
    onOpenDrawer: () -> Unit,
    onLogout: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    SettingsScreen(
        uiState = uiState,
        onOpenDrawer = onOpenDrawer,
        onLogout = onLogout,
        onProfileClick = onProfileClick,
        modifier = modifier,
    )
}
