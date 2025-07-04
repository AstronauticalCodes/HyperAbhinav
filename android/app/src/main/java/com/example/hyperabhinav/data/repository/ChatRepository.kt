package com.example.hyperabhinav.data.repository

import com.example.hyperabhinav.data.database.ChatDatabase
import com.example.hyperabhinav.data.database.MessageEntity
import com.example.hyperabhinav.data.GeminiApiClient
import com.example.hyperabhinav.ui.pages.chat.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepository(
    private val database: ChatDatabase,
    private val geminiApiClient: GeminiApiClient
) {

    private val messageDao = database.messageDao()

    // Get all messages as Flow for real-time updates
    fun getMessages(): Flow<List<Message>> {
        return messageDao.getMessagesForConversation().map { entities ->
            entities.map { entity ->
                Message(
                    text = entity.text,
                    isUser = entity.isUser
                )
            }
        }
    }

    // Save a message to the database
    suspend fun saveMessage(message: Message) {
        val messageEntity = MessageEntity(
            text = message.text,
            isUser = message.isUser,
            timestamp = System.currentTimeMillis()
        )
        messageDao.insertMessage(messageEntity)
    }

    // Send message to Gemini and save both user message and response
    suspend fun sendMessageToGemini(userMessage: String): String {
        // Save user message first
        saveMessage(Message(text = userMessage, isUser = true))

        // Get response from Gemini
        val response = geminiApiClient.sendMessage(userMessage)

        // Save Gemini response
        saveMessage(Message(text = response, isUser = false))

        return response
    }

    // Clear all chat history
    suspend fun clearChatHistory() {
        messageDao.deleteAllMessages()
        geminiApiClient.resetChatHistory()
    }

    // Get message count for checking if there's existing history
    suspend fun getMessageCount(): Int {
        return messageDao.getMessageCount()
    }

    // Initialize Gemini chat with existing history
    suspend fun initializeGeminiWithHistory() {
        val messages = messageDao.getAllMessages()
        // Convert to Gemini format and initialize chat
        // This would need to be implemented based on Gemini's history format
        // For now, we'll just reset the chat
        geminiApiClient.resetChatHistory()
    }
}
