package rip.haris.prismai.presentation.ui.screens.chathistory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rip.haris.prismai.presentation.viewmodel.ChatHistoryViewModel

@Composable
fun ChatHistoryRoute(
    onOpenDrawer: () -> Unit,
    onNewChat: () -> Unit,
    onChatClick: (id: Long, title: String) -> Unit,
    isDrawerOpen: Boolean,
    modifier: Modifier = Modifier,
    viewModel: ChatHistoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    ChatHistoryScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onOpenDrawer = onOpenDrawer,
        onNewChat = onNewChat,
        onChatClick = onChatClick,
        isDrawerOpen = isDrawerOpen,
        modifier = modifier,
    )
}
