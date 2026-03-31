package rip.haris.prismai.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import rip.haris.prismai.presentation.ui.screens.profile.components.DeleteAccountButton
import rip.haris.prismai.presentation.ui.screens.profile.components.ProfileActionButton
import rip.haris.prismai.presentation.ui.screens.profile.components.ProfileTextField
import rip.haris.prismai.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    label = "Full name",
                    value = state.fullName,
                    onValueChange = viewModel::onFullNameChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProfileTextField(
                    label = "What should we call you?",
                    value = state.displayName,
                    onValueChange = viewModel::onDisplayNameChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProfileActionButton(
                    text = "Update Profile",
                    enabled = state.isProfileChanged,
                    onClick = viewModel::onUpdateProfile
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "What should PrismAI keep in mind when responding?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "These preferences will apply to all conversations.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                ProfileTextField(
                    label = "",
                    value = state.preferences,
                    onValueChange = viewModel::onPreferencesChange,
                    placeholder = "Ask clarifying questions before giving detailed answers",
                    singleLine = false,
                    minLines = 4
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProfileActionButton(
                    text = "Save Preferences",
                    enabled = state.isPreferencesChanged,
                    onClick = viewModel::onSavePreferences
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = "Account Actions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                DeleteAccountButton()
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
