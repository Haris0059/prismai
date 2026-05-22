package rip.haris.prismai.presentation.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import rip.haris.prismai.R
import rip.haris.prismai.presentation.ui.screens.chat.components.ChatInputBar
import rip.haris.prismai.presentation.ui.screens.chat.components.MessageBubble
import rip.haris.prismai.presentation.ui.screens.chat.components.ModelBottomSheet
import rip.haris.prismai.presentation.ui.screens.chat.components.ModelSelectorButton
import rip.haris.prismai.presentation.ui.screens.chat.components.SuggestedPromptsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onInputChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    onSelectModel: (String) -> Unit,
    onShowModelSheet: (Boolean) -> Unit,
    onOpenDrawer: () -> Unit,
    isDrawerOpen: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            delay(100)
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    ModelSelectorButton(
                        selectedModel = uiState.selectedModel?.name.orEmpty(),
                        onClick = { onShowModelSheet(true) },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onOpenDrawer()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open drawer",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        bottomBar = {
            ChatInputBar(
                inputText = uiState.inputText,
                onInputChange = onInputChange,
                onSend = onSendMessage,
                hasMessages = uiState.messages.isNotEmpty(),
                canSend = uiState.canSend,
                isDrawerOpen = isDrawerOpen,
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        },
    ) { innerPadding ->
        if (uiState.messages.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_512),
                    contentDescription = "Prism AI Logo",
                    modifier = Modifier.size(36.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = uiState.greeting,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(32.dp))

                SuggestedPromptsRow(
                    prompts = uiState.suggestedPrompts,
                    onPromptClick = onInputChange,
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(uiState.messages) { message ->
                    MessageBubble(text = message.text, isUser = message.isUser)
                }
            }
        }
    }

    if (uiState.showModelSheet) {
        ModelBottomSheet(
            models = uiState.availableModels,
            selectedModel = uiState.selectedModel?.name.orEmpty(),
            onModelSelected = onSelectModel,
            onDismiss = { onShowModelSheet(false) },
        )
    }
}
