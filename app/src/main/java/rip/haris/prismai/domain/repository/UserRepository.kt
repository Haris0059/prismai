package rip.haris.prismai.domain.repository

import kotlinx.coroutines.flow.Flow
import rip.haris.prismai.domain.model.User

interface UserRepository {
    /** Emits the signed-in user's profile, updating in realtime as the Firestore doc changes. */
    fun observeCurrentUser(): Flow<User?>

    /** Writes the editable profile fields back to Firestore. */
    suspend fun update(user: User)
}
