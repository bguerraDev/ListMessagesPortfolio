package com.bryanguerra.listmessagesportfolio.ui.messages

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bryanguerra.listmessagesportfolio.data.repository.MessageRepository

class MessageViewModelFactory(
    private val application: Application,
    private val repository: MessageRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MessagesViewModel(application, repository) as T
    }
}