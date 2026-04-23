package rip.haris.prismai.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import rip.haris.prismai.presentation.ui.screens.chat.ChatScreen
import rip.haris.prismai.presentation.ui.screens.chathistory.ChatHistoryScreen
import rip.haris.prismai.presentation.ui.screens.login.LoginScreen
import rip.haris.prismai.presentation.ui.screens.profile.ProfileScreen
import rip.haris.prismai.presentation.ui.screens.settings.SettingsScreen

private const val SLIDE_MS = 300

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    viewModels: AppViewModels,
    onOpenDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
) {
    composable(
        route = Routes.LOGIN,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
    ) {
        val loginState by viewModels.login.state.collectAsState()
        LaunchedEffect(loginState.isLoggedIn) {
            if (loginState.isLoggedIn) {
                navController.navigate(Routes.CHAT) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        LoginScreen(viewModel = viewModels.login)
    }

    composable(
        route = Routes.CHAT,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) {
        ChatScreen(
            viewModel = viewModels.chat,
            onOpenDrawer = onOpenDrawer,
            isDrawerOpen = isDrawerOpen(),
        )
    }

    composable(
        route = Routes.CHAT_DETAIL,
        arguments = listOf(
            navArgument(Routes.ARG_CHAT_ID) { type = NavType.StringType },
            navArgument(Routes.ARG_TITLE) {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) { backStackEntry ->
        val chatId = backStackEntry.arguments?.getString(Routes.ARG_CHAT_ID).orEmpty()
        val title = backStackEntry.arguments?.getString(Routes.ARG_TITLE).orEmpty()
        LaunchedEffect(chatId) {
            viewModels.chat.loadChat(chatId, title)
        }
        ChatScreen(
            viewModel = viewModels.chat,
            onOpenDrawer = onOpenDrawer,
            isDrawerOpen = isDrawerOpen(),
        )
    }

    composable(
        route = Routes.CHAT_HISTORY,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) {
        ChatHistoryScreen(
            viewModel = viewModels.chatHistory,
            onOpenDrawer = onOpenDrawer,
            onNewChat = {
                viewModels.chat.onNewChat()
                navController.navigate(Routes.CHAT) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onChatClick = { id, title ->
                navController.navigate(Routes.chatDetail(id, title)) {
                    launchSingleTop = true
                }
            },
            isDrawerOpen = isDrawerOpen(),
        )
    }

    composable(
        route = Routes.SETTINGS,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) {
        SettingsScreen(
            viewModel = viewModels.settings,
            onOpenDrawer = onOpenDrawer,
            onLogout = {
                viewModels.login.onLogout()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onProfileClick = { navController.navigate(Routes.PROFILE) },
        )
    }

    composable(
        route = Routes.PROFILE,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) {
        ProfileScreen(
            viewModel = viewModels.profile,
            onBack = { navController.popBackStack() },
        )
    }
}
