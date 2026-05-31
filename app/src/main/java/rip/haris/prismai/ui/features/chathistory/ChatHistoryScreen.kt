package rip.haris.prismai.ui.features.chathistory

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import rip.haris.prismai.domain.model.Conversation
import rip.haris.prismai.ui.features.chathistory.components.ChatHistoryListItem
import rip.haris.prismai.ui.features.chathistory.components.ChatSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    uiState: ChatHistoryUiState,
    onSearchQueryChange: (String) -> Unit,
    onOpenDrawer: () -> Unit,
    onNewChat: () -> Unit,
    onChatClick: (Conversation) -> Unit,
    onRenameClick: (Conversation) -> Unit,
    onRenameTextChange: (String) -> Unit,
    onRenameConfirm: () -> Unit,
    onRenameDismiss: () -> Unit,
    onDelete: (Conversation) -> Unit,
    isDrawerOpen: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isDrawerOpen) {
        if (isDrawerOpen) focusManager.clearFocus()
    }

    uiState.renameTarget?.let { target ->
        AlertDialog(
            onDismissRequest = onRenameDismiss,
            title = { Text("Rename chat") },
            text = {
                OutlinedTextField(
                    value = uiState.renameText,
                    onValueChange = onRenameTextChange,
                    singleLine = true,
                    label = { Text("Title") },
                )
            },
            confirmButton = { TextButton(onClick = onRenameConfirm) { Text("Save") } },
            dismissButton = { TextButton(onClick = onRenameDismiss) { Text("Cancel") } },
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onOpenDrawer()
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewChat,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "New chat")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "New chat", style = MaterialTheme.typography.labelLarge)
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
        ) {
            item {
                Text(
                    text = "Chats",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }

            item {
                ChatSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            uiState.errorMessage?.let { message ->
                item {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                }
            }

            if (uiState.errorMessage == null) {
                if (uiState.isEmpty && !uiState.isLoading) {
                    item {
                        Text(
                            text = if (uiState.searchQuery.isBlank()) "No chats yet" else "No chats found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 24.dp),
                        )
                    }
                } else {
                    items(uiState.filteredChats, key = { it.id }) { conversation ->
                        ChatHistoryListItem(
                            title = conversation.title,
                            timeAgo = formatTimeAgo(conversation.updatedAtMillis),
                            onClick = { onChatClick(conversation) },
                            onRename = { onRenameClick(conversation) },
                            onDelete = { onDelete(conversation) },
                        )
                    }
                }
            }
        }
    }
}

private fun formatTimeAgo(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val minutes = diff / 60_000
    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "$minutes minutes ago"
        minutes < 1440 -> "${minutes / 60} hours ago"
        minutes < 10_080 -> "${minutes / 1440} days ago"
        else -> "${minutes / 10_080} weeks ago"
    }
}
