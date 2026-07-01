package rip.haris.prismai.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Streaming
import rip.haris.prismai.data.remote.dto.ChatRequestDto
import rip.haris.prismai.data.remote.dto.ConversationDetailDto
import rip.haris.prismai.data.remote.dto.ConversationDto
import rip.haris.prismai.data.remote.dto.DeleteResponseDto
import rip.haris.prismai.data.remote.dto.RenameRequestDto
import rip.haris.prismai.data.remote.dto.RenameResponseDto

/**
 * Retrofit interface for the PrismAI FastAPI backend.
 * Every call is authenticated by the OkHttp auth interceptor, which attaches the
 * current user's Firebase ID token as a Bearer header.
 */
interface PrismAiApi {

    @GET("conversations")
    suspend fun getConversations(): List<ConversationDto>

    @GET("conversations/{id}")
    suspend fun getConversation(@Path("id") id: String): ConversationDetailDto

    @PUT("conversations/{id}")
    suspend fun renameConversation(
        @Path("id") id: String,
        @Body body: RenameRequestDto,
    ): RenameResponseDto

    @DELETE("conversations/{id}")
    suspend fun deleteConversation(@Path("id") id: String): DeleteResponseDto

    /**
     * Streaming chat completion. The response is a `text/event-stream`; the caller
     * reads it line by line. Returns the raw [ResponseBody] so the repository can
     * parse the SSE frames.
     */
    @Streaming
    @POST("chat/{provider}")
    suspend fun chat(
        @Path("provider") provider: String,
        @Body body: ChatRequestDto,
    ): ResponseBody
}
