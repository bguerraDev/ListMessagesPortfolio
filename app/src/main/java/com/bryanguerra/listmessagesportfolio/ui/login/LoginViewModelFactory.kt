package com.bryanguerra.listmessagesportfolio.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bryanguerra.listmessagesportfolio.data.repository.MessageRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val application: Application,
    private val repository: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(application, repository) as T
    }
}