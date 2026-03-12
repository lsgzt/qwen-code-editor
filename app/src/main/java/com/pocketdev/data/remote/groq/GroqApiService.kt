package com.pocketdev.data.remote.groq

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Groq API Service Interface
 * Uses OpenAI-compatible chat completion API format
 */
interface GroqApiService {

    @POST("chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}

/**
 * Request body for Groq chat completion API
 */
data class ChatRequest(
    val model: String = "llama-3.3-70b-versatile",
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1024,
    val stream: Boolean = false
)

/**
 * Message in chat conversation
 */
data class Message(
    val role: String, // "system", "user", or "assistant"
    val content: String
)

/**
 * Response from Groq chat completion API
 */
data class ChatResponse(
    val id: String?,
    val choices: List<Choice>?,
    val created: Long?,
    val model: String?,
    val usage: Usage?
)

/**
 * Choice in chat response
 */
data class Choice(
    val index: Int?,
    val message: Message?,
    val finish_reason: String?
)

/**
 * Token usage information
 */
data class Usage(
    val prompt_tokens: Int?,
    val completion_tokens: Int?,
    val total_tokens: Int?
)

/**
 * Error response from Groq API
 */
data class GroqError(
    val error: ErrorDetail?
)

data class ErrorDetail(
    val message: String?,
    val type: String?,
    val code: String?
)
