package rip.haris.prismai.data.session

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _isLoggedIn = MutableStateFlow(prefs.getBoolean(KEY_LOGGED_IN, false))
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login() {
        prefs.edit { putBoolean(KEY_LOGGED_IN, true) }
        _isLoggedIn.value = true
    }

    fun logout() {
        prefs.edit { putBoolean(KEY_LOGGED_IN, false) }
        _isLoggedIn.value = false
    }

    private companion object {
        const val PREFS_NAME = "prismai_session"
        const val KEY_LOGGED_IN = "is_logged_in"
    }
}
