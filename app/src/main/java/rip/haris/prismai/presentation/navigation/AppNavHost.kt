package rip.haris.prismai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    onOpenDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        appNavGraph(
            navController = navController,
            onOpenDrawer = onOpenDrawer,
            isDrawerOpen = isDrawerOpen,
            onLogout = onLogout,
        )
    }
}
