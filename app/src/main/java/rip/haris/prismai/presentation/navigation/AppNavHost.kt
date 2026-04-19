package rip.haris.prismai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    viewModels: AppViewModels,
    onOpenDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        appNavGraph(
            navController = navController,
            viewModels = viewModels,
            onOpenDrawer = onOpenDrawer,
            isDrawerOpen = isDrawerOpen,
        )
    }
}
