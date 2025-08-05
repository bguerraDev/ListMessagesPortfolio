package com.bryanguerra.listmessagesportfolio.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryanguerra.listmessagesportfolio.data.domain.model.LoginRequest
import com.bryanguerra.listmessagesportfolio.data.repository.MessageRepository
import com.bryanguerra.listmessagesportfolio.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    application: Application,
    private val repository: MessageRepository

) : AndroidViewModel(application) {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(LoginRequest(username, password))
                // Guardar token en datastore
                UserPreferences.saveAccessToken(getApplication(), response.access)

                _loginState.value = LoginState.Success
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login incorrecto")
            }
        }
    }
}