package rip.haris.prismai.data.remote.interceptor

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Attaches the signed-in user's Firebase ID token to every outbound request as
 * `Authorization: Bearer <token>`. The token is fetched on the OkHttp dispatcher
 * thread via [runBlocking]; a short timeout guards against a hung token refresh.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val auth: FirebaseAuth,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            withTimeoutOrNull(TOKEN_TIMEOUT_MS) {
                auth.currentUser?.getIdToken(false)?.await()?.token
            }
        }

        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }

    private companion object {
        const val TOKEN_TIMEOUT_MS = 5_000L
    }
}
