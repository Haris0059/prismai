package rip.haris.prismai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import rip.haris.prismai.presentation.theme.PrismAITheme
import rip.haris.prismai.presentation.ui.screens.chat.ChatScreen
import rip.haris.prismai.presentation.ui.screens.chat.components.DrawerContent
import rip.haris.prismai.presentation.ui.screens.chathistory.ChatHistoryScreen
import rip.haris.prismai.presentation.ui.screens.login.LoginScreen
import rip.haris.prismai.presentation.ui.screens.profile.ProfileScreen
import rip.haris.prismai.presentation.ui.screens.settings.SettingsScreen
import rip.haris.prismai.data.model.sampleChats
import rip.haris.prismai.presentation.viewmodel.ChatHistoryViewModel
import rip.haris.prismai.presentation.viewmodel.ChatViewModel
import rip.haris.prismai.presentation.viewmodel.LoginViewModel
import rip.haris.prismai.presentation.viewmodel.ProfileViewModel
import rip.haris.prismai.presentation.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel = LoginViewModel()
    private val chatViewModel = ChatViewModel()
    private val chatHistoryViewModel = ChatHistoryViewModel()
    private val settingsViewModel = SettingsViewModel()
    private val profileViewModel = ProfileViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrismAITheme {
                val loginState by loginViewModel.state.collectAsState()
                var currentScreen by remember { mutableStateOf("chat") }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val recentChats = remember {
                    sampleChats.take(11).map { it.title }
                }

                if (!loginState.isLoggedIn) {
                    LoginScreen(viewModel = loginViewModel)
                } else {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            DrawerContent(
                                recentChats = recentChats,
                                onNewChat = {
                                    chatViewModel.onNewChat()
                                    currentScreen = "chat"
                                    scope.launch { drawerState.close() }
                                },
                                onRecentChatClick = {
                                    scope.launch { drawerState.close() }
                                },
                                onChatsClick = {
                                    currentScreen = "chatHistory"
                                    scope.launch { drawerState.close() }
                                },
                                onSettingsClick = {
                                    currentScreen = "settings"
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    ) {
                        when (currentScreen) {
                            "profile" -> ProfileScreen(
                                viewModel = profileViewModel,
                                onBack = { currentScreen = "settings" }
                            )
                            "settings" -> SettingsScreen(
                                viewModel = settingsViewModel,
                                onOpenDrawer = { scope.launch { drawerState.open() } },
                                onLogout = {
                                    currentScreen = "chat"
                                    loginViewModel.onLogout()
                                },
                                onProfileClick = { currentScreen = "profile" }
                            )
                            "chatHistory" -> ChatHistoryScreen(
                                viewModel = chatHistoryViewModel,
                                onOpenDrawer = { scope.launch { drawerState.open() } },
                                onNewChat = {
                                    chatViewModel.onNewChat()
                                    currentScreen = "chat"
                                }
                            )
                            else -> ChatScreen(
                                viewModel = chatViewModel,
                                onOpenDrawer = { scope.launch { drawerState.open() } },
                                isDrawerOpen = drawerState.targetValue == DrawerValue.Open
                            )
                        }
                    }
                }
            }
        }
    }
}
