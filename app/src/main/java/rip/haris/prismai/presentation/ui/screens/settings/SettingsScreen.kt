package rip.haris.prismai.presentation.ui.screens.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rip.haris.prismai.presentation.ui.screens.settings.components.SettingsMenuItem
import rip.haris.prismai.presentation.ui.screens.settings.components.SettingsUserCard
import rip.haris.prismai.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onOpenDrawer: () -> Unit,
    onLogout: () -> Unit,
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            item {
                SettingsUserCard(
                    email = state.userEmail,
                    isPro = state.isPro
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Account section
            item {
                SettingsMenuItem(
                    icon = Icons.Default.Person,
                    label = "Profile",
                    onClick = onProfileClick
                )
            }

            item {
                SettingsMenuItem(
                    icon = Icons.Default.Payments,
                    label = "Billing",
                    onClick = { }
                )
            }

            item {
                SettingsMenuItem(
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    label = "Usage",
                    onClick = { }
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Preferences section
            item {
                SettingsMenuItem(
                    icon = Icons.Default.Shield,
                    label = "Permissions",
                    onClick = { }
                )
            }

            item {
                SettingsMenuItem(
                    icon = Icons.Default.Notifications,
                    label = "Notifications",
                    onClick = { }
                )
            }

            item {
                SettingsMenuItem(
                    icon = Icons.Default.Security,
                    label = "Privacy",
                    onClick = { }
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Log out
            item {
                SettingsMenuItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    label = "Log out",
                    onClick = onLogout,
                    labelColor = MaterialTheme.colorScheme.error,
                    iconColor = MaterialTheme.colorScheme.error
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
