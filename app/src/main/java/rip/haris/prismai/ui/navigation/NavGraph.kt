package rip.haris.prismai.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import rip.haris.prismai.ui.features.chat.ChatRoute
import rip.haris.prismai.ui.features.chathistory.ChatHistoryRoute
import rip.haris.prismai.ui.features.login.LoginRoute
import rip.haris.prismai.ui.features.profile.ProfileRoute
import rip.haris.prismai.ui.features.settings.SettingsRoute

private const val SLIDE_MS = 300

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    onOpenDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    onLogout: () -> Unit,
    onStartNewChat: () -> Unit,
) {
    composable(
        route = Routes.LOGIN,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
    ) {
        LoginRoute(
            onLoggedIn = {
                navController.navigate(Routes.CHAT) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            },
        )
    }

    composable(
        route = Routes.CHAT,
        enterTransition = { slideInHorizontally(tween(SLIDE_MS)) { it } },
        exitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { -it } },
        popEnterTransition = { slideInHorizontally(tween(SLIDE_MS)) { -it } },
        popExitTransition = { slideOutHorizontally(tween(SLIDE_MS)) { it } },
    ) {
        ChatRoute(
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
    ) {
        ChatRoute(
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
        ChatHistoryRoute(
            onOpenDrawer = onOpenDrawer,
            onNewChat = {
                onStartNewChat()
                navController.navigate(Routes.CHAT) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onChatClick = { id, title ->
                navController.navigate(Routes.chatDetail(id, title)) {
                    // Replace any currently-open conversation so a fresh ChatViewModel
                    // is created for the newly selected one.
                    popUpTo(Routes.CHAT_DETAIL) { inclusive = true }
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
        SettingsRoute(
            onOpenDrawer = onOpenDrawer,
            onLogout = {
                onLogout()
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
        ProfileRoute(onBack = { navController.popBackStack() })
    }
}
