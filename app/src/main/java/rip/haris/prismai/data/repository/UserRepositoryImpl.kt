package rip.haris.prismai.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import rip.haris.prismai.data.session.SessionManager
import rip.haris.prismai.domain.model.User
import rip.haris.prismai.domain.repository.UserRepository
import rip.haris.prismai.ui.common.SnackbarController

/**
 * Firestore-backed user profile. The profile lives at `users/{uid}` and is observed via
 * a realtime snapshot listener, so the UI reacts to changes immediately (and across devices).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore,
    private val snackbar: SnackbarController,
) : UserRepository {

    override fun observeCurrentUser(): Flow<User?> =
        sessionManager.currentUser.flatMapLatest { fbUser ->
            if (fbUser == null) flowOf(null) else observeUserDoc(fbUser)
        }

    private fun observeUserDoc(fbUser: FirebaseUser): Flow<User?> = callbackFlow {
        val docRef = firestore.collection(USERS).document(fbUser.uid)
        val registration = docRef.addSnapshotListener { snapshot, error ->
            when {
                error != null -> trySend(fbUser.toFallbackUser())
                snapshot != null && snapshot.exists() -> trySend(snapshot.toUser(fbUser))
                snapshot != null -> {
                    // First access for this user: create a default profile document.
                    docRef.set(defaultProfile(fbUser))
                    trySend(fbUser.toFallbackUser())
                }
            }
        }
        awaitClose { registration.remove() }
    }

    override suspend fun update(user: User) {
        val uid = sessionManager.currentUser.value?.uid ?: return
        // Don't await the server round-trip: Firestore applies the write to its local
        // cache immediately and syncs when back online. Only surface a Snackbar on a
        // genuine rejection (e.g. a security-rule denial), not on offline-pending writes.
        firestore.collection(USERS).document(uid).set(
            mapOf(
                FULL_NAME to user.fullName,
                DISPLAY_NAME to user.displayName,
                IS_PRO to user.isPro,
                HAPTIC_ON to user.hapticOn,
                PREFERENCES to user.preferences,
            ),
            SetOptions.merge(),
        ).addOnFailureListener { e ->
            snackbar.show("Couldn't save changes: ${e.message ?: "network error"}")
        }
    }

    private fun defaultProfile(fbUser: FirebaseUser) = mapOf(
        FULL_NAME to fbUser.displayName.orEmpty(),
        DISPLAY_NAME to fbUser.displayName.orEmpty(),
        IS_PRO to false,
        HAPTIC_ON to true,
    )

    private fun FirebaseUser.toFallbackUser() = User(
        email = email.orEmpty(),
        fullName = displayName.orEmpty(),
        displayName = displayName.orEmpty(),
        isPro = false,
        hapticOn = true,
    )

    private fun DocumentSnapshot.toUser(fbUser: FirebaseUser) = User(
        email = fbUser.email.orEmpty(),
        fullName = getString(FULL_NAME) ?: fbUser.displayName.orEmpty(),
        displayName = getString(DISPLAY_NAME) ?: fbUser.displayName.orEmpty(),
        isPro = getBoolean(IS_PRO) ?: false,
        hapticOn = getBoolean(HAPTIC_ON) ?: true,
        preferences = getString(PREFERENCES).orEmpty(),
    )

    private companion object {
        const val USERS = "users"
        const val FULL_NAME = "fullName"
        const val DISPLAY_NAME = "displayName"
        const val IS_PRO = "isPro"
        const val HAPTIC_ON = "hapticOn"
        const val PREFERENCES = "preferences"
    }
}
