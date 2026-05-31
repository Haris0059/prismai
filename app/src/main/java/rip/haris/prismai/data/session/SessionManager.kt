package rip.haris.prismai.data.session

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

/**
 * Thin wrapper over [FirebaseAuth]. Persistent login comes for free from the SDK:
 * [FirebaseAuth.getCurrentUser] is restored synchronously on process start, and an
 * [FirebaseAuth.AuthStateListener] keeps the exposed flows in sync.
 */
@Singleton
class SessionManager @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()

    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
            _isLoggedIn.value = firebaseAuth.currentUser != null
        }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
        Unit
    }

    suspend fun signUp(email: String, password: String, displayName: String): Result<Unit> =
        runCatching {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            if (displayName.isNotBlank()) {
                result.user?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
                )?.await()
            }
            Unit
        }

    fun signOut() = auth.signOut()
}
