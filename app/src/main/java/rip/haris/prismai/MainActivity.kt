package rip.haris.prismai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import rip.haris.prismai.data.model.sampleChats
import rip.haris.prismai.presentation.navigation.AppNavHost
import rip.haris.prismai.presentation.navigation.AppViewModels
import rip.haris.prismai.presentation.navigation.Routes
import rip.haris.prismai.presentation.theme.PrismAITheme
import rip.haris.prismai.presentation.ui.components.DrawerContent
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
                val viewModels = remember {
                    AppViewModels(
                        login = loginViewModel,
                        chat = chatViewModel,
                        chatHistory = chatHistoryViewModel,
                        settings = settingsViewModel,
                        profile = profileViewModel,
                    )
                }

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

                val loginState by loginViewModel.state.collectAsState()
                val profileState by profileViewModel.state.collectAsState()

                val startDestination = remember {
                    if (loginState.isLoggedIn) Routes.CHAT else Routes.LOGIN
                }

                val recentChats = remember { sampleChats.map { it.title } }

                val drawerEnabled = currentRoute != null && currentRoute != Routes.LOGIN

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = drawerEnabled,
                    drawerContent = {
                        DrawerContent(
                            recentChats = recentChats,
                            onNewChat = {
                                chatViewModel.onNewChat()
                                navController.navigate(Routes.CHAT) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            },
                            onRecentChatClick = {
                                scope.launch { drawerState.close() }
                            },
                            onChatsClick = {
                                navController.navigate(Routes.CHAT_HISTORY) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            },
                            onSettingsClick = {
                                navController.navigate(Routes.SETTINGS) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            },
                            userName = profileState.savedFullName
                        )
                    }
                ) {
                    AppNavHost(
                        navController = navController,
                        startDestination = startDestination,
                        viewModels = viewModels,
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        isDrawerOpen = { drawerState.targetValue == DrawerValue.Open },
                    )
                }
            }
        }
    }
}
