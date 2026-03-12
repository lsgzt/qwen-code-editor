package com.pocketdev.data.remote.groq

import com.pocketdev.domain.model.AiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Repository for Groq AI API interactions
 */
class GroqRepository(private val apiKey: String) {

    private val apiService: GroqApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.groq.com/openai/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(GroqApiService::class.java)
    }

    /**
     * Fix bugs in code using Groq AI
     */
    suspend fun fixBug(code: String, language: String): Result<AiResponse> = withContext(Dispatchers.IO) {
        try {
            val prompt = """Analyze this $language code and identify any bugs, errors, or issues. Provide the corrected code with clear explanations of what was wrong.

Code:
```$language
$code
```

Respond in this format:
1. Brief explanation of issues found
2. Corrected code in a code block"""

            val response = callGroqApi(prompt)
            response.fold(
                onSuccess = { aiResponse -> Result.success(aiResponse) },
                onFailure = { error -> Result.failure(error) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Explain code using Groq AI
     */
    suspend fun explainCode(code: String, language: String): Result<AiResponse> = withContext(Dispatchers.IO) {
        try {
            val prompt = """Explain this $language code in simple, beginner-friendly terms. Break down what each part does step-by-step.

Code:
```$language
$code
```

Provide a clear, educational explanation suitable for someone learning to code."""

            val response = callGroqApi(prompt)
            response.fold(
                onSuccess = { aiResponse -> Result.success(aiResponse) },
                onFailure = { error -> Result.failure(error) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Improve code using Groq AI
     */
    suspend fun improveCode(code: String, language: String): Result<AiResponse> = withContext(Dispatchers.IO) {
        try {
            val prompt = """Suggest improvements for this $language code. Focus on: best practices, performance optimization, readability, and maintainability. Provide improved code with explanations.

Code:
```$language
$code
```

Respond with:
1. List of specific improvements
2. Improved code in a code block
3. Brief explanation of changes made"""

            val response = callGroqApi(prompt)
            response.fold(
                onSuccess = { aiResponse -> 
                    val improved = AiResponse(
                        content = aiResponse.content,
                        originalCode = code,
                        suggestedCode = extractCodeBlock(aiResponse.content),
                        improvements = extractImprovements(aiResponse.content)
                    )
                    Result.success(improved)
                },
                onFailure = { error -> Result.failure(error) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Make direct API call to Groq
     */
    private suspend fun callGroqApi(prompt: String): Result<AiResponse> = withContext(Dispatchers.IO) {
        try {
            val request = ChatRequest(
                model = "llama-3.3-70b-versatile",
                messages = listOf(
                    Message("system", "You are a helpful coding assistant. Provide clear, accurate, and educational responses."),
                    Message("user", prompt)
                ),
                temperature = 0.7,
                max_tokens = 1024
            )

            val response = apiService.chatCompletion("Bearer $apiKey", request)
            
            if (response.isSuccessful && response.body() != null) {
                val content = response.body()?.choices?.firstOrNull()?.message?.content ?: ""
                if (content.isNotEmpty()) {
                    Result.success(AiResponse(content = content, originalCode = ""))
                } else {
                    Result.failure(IOException("Empty response from AI"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(IOException("API Error: $errorMsg"))
            }
        } catch (e: IOException) {
            Result.failure(IOException("Network error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Extract code block from AI response
     */
    private fun extractCodeBlock(text: String): String? {
        val pattern = Regex("```(?:\\w+)?\n(.*?)```", RegexOption.DOT_MATCHES_ALL)
        return pattern.find(text)?.groupValues?.getOrNull(1)?.trim()
    }

    /**
     * Extract improvement list from AI response
     */
    private fun extractImprovements(text: String): List<String> {
        val lines = text.split("\n")
        return lines.filter { it.trim().startsWith("-") || it.trim().startsWith("*") || it.trim().matches(Regex("^\\d+\\.\\s")) }
            .map { it.trim().removePrefix("-").removePrefix("*").removePrefix(".").trim() }
    }

    companion object {
        private const val TAG = "GroqRepository"
    }
}
