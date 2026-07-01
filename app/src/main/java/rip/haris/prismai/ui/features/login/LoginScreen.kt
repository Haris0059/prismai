package rip.haris.prismai.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rip.haris.prismai.ui.common.LoadStatus
import rip.haris.prismai.ui.features.login.components.EmailInputField
import rip.haris.prismai.ui.features.login.components.GoogleSignInButton
import rip.haris.prismai.ui.features.login.components.OrDivider
import rip.haris.prismai.ui.features.login.components.PasswordInputField
import rip.haris.prismai.ui.features.login.components.TermsText

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onToggleMode: () -> Unit,
    onGoogleSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSignUp = uiState.mode == AuthMode.SIGN_UP

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 64.dp),
        ) {
            Text(
                text = "PrismAI",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (isSignUp) "Create your\nPrismAI account" else "Do your best work\nwith PrismAI",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(32.dp))

            GoogleSignInButton(onClick = onGoogleSignIn)

            Spacer(modifier = Modifier.height(16.dp))

            OrDivider()

            Spacer(modifier = Modifier.height(16.dp))

            EmailInputField(
                email = uiState.email,
                onEmailChange = onEmailChange,
                onSubmit = onSubmit,
                errorMessage = uiState.emailError,
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordInputField(
                password = uiState.password,
                onPasswordChange = onPasswordChange,
                onSubmit = onSubmit,
                errorMessage = uiState.passwordError,
            )

            val status = uiState.status
            if (status is LoadStatus.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = status.message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSubmit,
                enabled = uiState.isValid && !uiState.isSubmitting,
                shape = RoundedCornerShape(26.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) {
                if (uiState.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(22.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Text(text = if (isSignUp) "Create account" else "Sign in")
                }
            }

            TextButton(onClick = onToggleMode) {
                Text(
                    text = if (isSignUp) {
                        "Already have an account? Sign in"
                    } else {
                        "Don't have an account? Sign up"
                    },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TermsText(modifier = Modifier.padding(horizontal = 8.dp))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp),
        ) {
            Text(
                text = "PRISMAI",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
