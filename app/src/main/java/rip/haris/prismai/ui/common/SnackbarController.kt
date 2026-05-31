package rip.haris.prismai.ui.common

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * App-wide bus for transient error/feedback messages. Repositories and ViewModels
 * call [show]; the host (MainActivity) collects [messages] and displays a Snackbar.
 */
@Singleton
class SnackbarController @Inject constructor() {

    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val messages: SharedFlow<String> = _messages

    fun show(message: String) {
        _messages.tryEmit(message)
    }
}
