package com.example.hyperabhinav.ui.pages.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyperabhinav.data.GeminiApiClient
import com.example.hyperabhinav.data.database.ChatDatabase
import com.example.hyperabhinav.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Message(val text: String, val isUser: Boolean)

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val currentInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = ChatDatabase.getDatabase(application)
    private val repository = ChatRepository(database, GeminiApiClient())

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Load existing chat history when ViewModel is created
        loadChatHistory()
    }

    private fun loadChatHistory() {
        viewModelScope.launch {
            repository.getMessages().collect { messages ->
                _uiState.update { currentState ->
                    currentState.copy(messages = messages)
                }
            }
        }
    }

    fun updateCurrentInput(newInput: String) {
        _uiState.update { it.copy(currentInput = newInput) }
    }

    fun sendMessage() {
        val userMessage = _uiState.value.currentInput.trim()
        if (userMessage.isBlank()) return

        _uiState.update {
            it.copy(
                currentInput = "",
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                repository.sendMessageToGemini(userMessage)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to send message: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun clearChatHistory() {
        viewModelScope.launch {
            repository.clearChatHistory()
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}