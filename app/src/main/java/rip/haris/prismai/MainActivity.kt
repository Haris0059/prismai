package rip.haris.prismai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rip.haris.prismai.presentation.theme.PrismAITheme
import rip.haris.prismai.presentation.ui.screens.chat.ChatScreen
import rip.haris.prismai.presentation.ui.screens.chathistory.ChatHistoryScreen
import rip.haris.prismai.presentation.ui.screens.login.LoginScreen
import rip.haris.prismai.presentation.viewmodel.ChatHistoryViewModel
import rip.haris.prismai.presentation.viewmodel.ChatViewModel
import rip.haris.prismai.presentation.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel = LoginViewModel()
    private val chatViewModel = ChatViewModel()
    private val chatHistoryViewModel = ChatHistoryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrismAITheme {
                val loginState by loginViewModel.state.collectAsState()
                var showChatHistory by remember { mutableStateOf(false) }

                if (!loginState.isLoggedIn) {
                    LoginScreen(viewModel = loginViewModel)
                } else if (showChatHistory) {
                    ChatHistoryScreen(
                        viewModel = chatHistoryViewModel,
                        onBackToChat = { showChatHistory = false }
                    )
                } else {
                    ChatScreen(
                        viewModel = chatViewModel,
                        onNavigateToChatHistory = { showChatHistory = true }
                    )
                }
            }
        }
    }
}
