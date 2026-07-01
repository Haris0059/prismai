package rip.haris.prismai.ui.features.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rip.haris.prismai.ui.features.chat.ChatViewModel

@Composable
fun ChatRoute(
    onOpenDrawer: () -> Unit,
    isDrawerOpen: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    ChatScreen(
        uiState = uiState,
        onInputChange = viewModel::onInputChange,
        onSendMessage = viewModel::onSendMessage,
        onSelectModel = viewModel::onSelectModel,
        onShowModelSheet = viewModel::onShowModelSheet,
        onOpenDrawer = onOpenDrawer,
        isDrawerOpen = isDrawerOpen,
        modifier = modifier,
    )
}
