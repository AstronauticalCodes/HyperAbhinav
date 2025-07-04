package com.example.hyperabhinav.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.example.hyperabhinav.BuildConfig

class GeminiApiClient {

    // IMPORTANT: Replace "gemini-1.5-flash" with the ID of your fine-tuned model
    // if you specifically want to use that. Otherwise, "gemini-1.5-flash"
    // is a good default for free tier usage.
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-lite", // e.g., "gemini-1.5-flash" or your custom model ID
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private var chatSession = generativeModel.startChat(history = emptyList())

    suspend fun sendMessage(message: String): String {
        return try {
            val response = chatSession.sendMessage(message)
            response.text ?: "No response received."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.localizedMessage}"
        }
    }

    // Optional: If you want to reset the conversation history
    fun resetChatHistory() {
        chatSession = generativeModel.startChat(history = emptyList())
    }

    // Optional: If you want to initialize chat with a specific history
    fun initChatWithHistory(history: List<com.google.ai.client.generativeai.type.Content>) {
        chatSession = generativeModel.startChat(history = history)
    }
}