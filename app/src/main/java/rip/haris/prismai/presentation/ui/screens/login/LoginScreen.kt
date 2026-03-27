package rip.haris.prismai.presentation.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rip.haris.prismai.presentation.ui.screens.login.components.EmailInputField
import rip.haris.prismai.presentation.ui.screens.login.components.GoogleSignInButton
import rip.haris.prismai.presentation.ui.screens.login.components.OrDivider
import rip.haris.prismai.presentation.ui.screens.login.components.TermsText
import rip.haris.prismai.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section - Logo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 64.dp)
        ) {
            Text(
                text = "PrismAI",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Middle section - Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Do your best work\nwith PrismAI",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(40.dp))

            GoogleSignInButton(
                onClick = { viewModel.onGoogleSignIn() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            OrDivider()

            Spacer(modifier = Modifier.height(20.dp))

            EmailInputField(
                email = state.email,
                onEmailChange = { viewModel.onEmailChange(it) },
                onSubmit = { viewModel.onEmailSubmit() },
                errorMessage = state.emailError
            )

            Spacer(modifier = Modifier.height(24.dp))

            TermsText(
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Bottom section - Branding
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = "PRISMAI",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
