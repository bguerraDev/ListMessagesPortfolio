package com.bryanguerra.listmessagesportfolio.ui.messages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bryanguerra.listmessagesportfolio.data.domain.model.MessageDisplay
import com.bryanguerra.listmessagesportfolio.data.repository.MessageRepository
import com.bryanguerra.listmessagesportfolio.utils.UserPreferences
import com.bryanguerra.listmessagesportfolio.utils.formatTimestampToSpanish
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MessagesViewModel(

    application: Application,
    private val repository: MessageRepository

) : AndroidViewModel(application) {

    private val _messages = MutableStateFlow<List<MessageDisplay>>(emptyList())
    val messages: StateFlow<List<MessageDisplay>> = _messages

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isAscending = MutableStateFlow(false) // por defecto descendente (más nuevo arriba)
    val isAscending: StateFlow<Boolean> = _isAscending

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMessages()
    }

    fun fetchMessages() {
        viewModelScope.launch {
            _isLoading.value = true

            UserPreferences.accessToken(getApplication()).collect { token ->

                if (!token.isNullOrBlank()) {
                    try {

                        val response = repository.getMessages(token)
                        val ordered = response.sortedByDescending { it.timestamp }
                        val displayList = ordered.map {
                            MessageDisplay(
                                id = it.id,
                                name = it.name,
                                email = it.email,
                                message = it.message,
                                timestamp = it.timestamp,
                                formattedDate = formatTimestampToSpanish(it.timestamp)
                            )
                        }
                        _messages.value = displayList
                    } catch (e: Exception) {
                        _error.value = e.message ?: "Error desconocido"
                    } finally {
                        _isLoading.value = false
                    }
                } else {
                    _error.value = "Token no encontrado"
                    _isLoading.value = false
                }
            }

        }
    }

    val filteredMessages: StateFlow<List<MessageDisplay>> =
        combine(messages, searchQuery, isAscending) { list, query, asc ->
            val filtered = if (query.isBlank()) list else list.filter {
                it.message.contains(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true) ||
                        it.email.contains(query, ignoreCase = true) ||
                        it.timestamp.contains(query, ignoreCase = true) ||
                        it.formattedDate.contains(query, ignoreCase = true)
            }

            if (asc) filtered.sortedBy { it.timestamp } else filtered.sortedByDescending { it.timestamp }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            UserPreferences.clearAccessToken(getApplication())
            onLoggedOut() // notifica al UI para redirigir al login
        }
    }

    fun toggleOrder() {
        _isAscending.value = !_isAscending.value
    }

}