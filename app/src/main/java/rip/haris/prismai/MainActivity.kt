package rip.haris.prismai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rip.haris.prismai.presentation.navigation.AppNavHost
import rip.haris.prismai.presentation.navigation.Routes
import rip.haris.prismai.presentation.theme.PrismAITheme
import rip.haris.prismai.presentation.ui.components.DrawerContent
import rip.haris.prismai.presentation.viewmodel.RootViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val rootViewModel: RootViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrismAITheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

                val currentUser by rootViewModel.currentUser.collectAsState()
                val recentChats by rootViewModel.recentChats.collectAsState()
                val isLoggedIn by rootViewModel.isLoggedIn.collectAsState()

                val drawerEnabled = currentRoute != null && currentRoute != Routes.LOGIN

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = drawerEnabled,
                    drawerContent = {
                        DrawerContent(
                            recentChats = recentChats,
                            onNewChat = {
                                navController.navigate(Routes.CHAT) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                scope.launch { drawerState.close() }
                            },
                            onRecentChatClick = { id, title ->
                                navController.navigate(Routes.chatDetail(id, title)) {
                                    launchSingleTop = true
                                }
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
                            userName = currentUser?.fullName ?: "",
                        )
                    },
                ) {
                    AppNavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) Routes.CHAT else Routes.LOGIN,
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        isDrawerOpen = { drawerState.targetValue == DrawerValue.Open },
                        onLogout = { rootViewModel.logout() },
                    )
                }
            }
        }
    }
}
