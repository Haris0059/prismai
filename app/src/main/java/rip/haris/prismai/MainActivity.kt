package rip.haris.prismai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import rip.haris.prismai.presentation.theme.PrismAITheme
import rip.haris.prismai.presentation.ui.screens.chat.ChatScreen
import rip.haris.prismai.presentation.ui.screens.login.LoginScreen
import rip.haris.prismai.presentation.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrismAITheme {
                val loginState by loginViewModel.state.collectAsState()

                if (loginState.isLoggedIn) {
                    ChatScreen()
                } else {
                    LoginScreen(viewModel = loginViewModel)
                }
            }
        }
    }
}
