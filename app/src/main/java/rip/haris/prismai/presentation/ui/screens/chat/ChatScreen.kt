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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rip.haris.prismai.R
import rip.haris.prismai.presentation.ui.screens.chat.components.ChatInputBar
import rip.haris.prismai.presentation.ui.screens.chat.components.DrawerContent
import rip.haris.prismai.presentation.ui.screens.chat.components.MessageBubble
import rip.haris.prismai.presentation.ui.screens.chat.components.ModelBottomSheet
import rip.haris.prismai.presentation.ui.screens.chat.components.ModelSelectorButton
import rip.haris.prismai.presentation.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, modifier: Modifier = Modifier) {
    val chatState by viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val recentChats = remember {
        (1..8).map { "Sample #$it" }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                recentChats = recentChats,
                onNewChat = {
                    viewModel.onNewChat()
                    scope.launch { drawerState.close() }
                },
                onRecentChatClick = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    ModelSelectorButton(
                        selectedModel = chatState.selectedModel,
                        onClick = { viewModel.onShowModelSheet(true) }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open drawer"
                        )
                    }
                },
                actions = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            ChatInputBar(
                inputText = chatState.inputText,
                onInputChange = { viewModel.onInputChange(it) },
                onSend = { viewModel.onSendMessage() },
                hasMessages = chatState.messages.isNotEmpty(),
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    ) { innerPadding ->
        if (chatState.messages.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_512),
                    contentDescription = "Prism AI Logo",
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = chatState.greeting,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        } else {
            // Messages list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(chatState.messages) { message ->
                    MessageBubble(
                        text = message.text,
                        isUser = message.isUser
                    )
                }
            }
        }
    }
    } // ModalNavigationDrawer end

    if (chatState.showModelSheet) {
        ModelBottomSheet(
            selectedModel = chatState.selectedModel,
            onModelSelected = { model -> viewModel.onModelSelected(model) },
            onDismiss = { viewModel.onShowModelSheet(false) }
        )
    }
}
