package com.example.hyperabhinav.ui.pages.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.your_app_package.data.GeminiApiClient
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
    private val geminiApiClient: GeminiApiClient = GeminiApiClient() // Provide a default for simplicity here,
    // or use Hilt/Koin for proper DI
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun updateCurrentInput(newInput: String) {
        _uiState.update { it.copy(currentInput = newInput) }
    }

    fun sendMessage() {
        val userMessage = _uiState.value.currentInput.trim()
        if (userMessage.isBlank()) return

        _uiState.update {
            it.copy(
                messages = it.messages + Message(text = userMessage, isUser = true),
                currentInput = "",
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            val response = geminiApiClient.sendMessage(userMessage)
            _uiState.update { currentState ->
                currentState.copy(
                    messages = currentState.messages + Message(text = response, isUser = false),
                    isLoading = false
                )
            }
        }
    }

    fun resetChat() {
        geminiApiClient.resetChatHistory()
        _uiState.update {
            ChatUiState() // Reset to initial state
        }
    }
}