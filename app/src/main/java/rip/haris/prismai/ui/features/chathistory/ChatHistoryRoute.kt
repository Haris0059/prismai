package rip.haris.prismai.ui.features.chathistory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatHistoryRoute(
    onOpenDrawer: () -> Unit,
    onNewChat: () -> Unit,
    onChatClick: (id: String, title: String) -> Unit,
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
        onChatClick = { conversation -> onChatClick(conversation.id, conversation.title) },
        onRenameClick = viewModel::onRenameClick,
        onRenameTextChange = viewModel::onRenameTextChange,
        onRenameConfirm = viewModel::onRenameConfirm,
        onRenameDismiss = viewModel::onRenameDismiss,
        onDelete = viewModel::onDelete,
        isDrawerOpen = isDrawerOpen,
        modifier = modifier,
    )
}
