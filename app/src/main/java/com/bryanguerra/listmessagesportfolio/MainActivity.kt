package com.bryanguerra.listmessagesportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.bryanguerra.listmessagesportfolio.data.network.RetrofitClient
import com.bryanguerra.listmessagesportfolio.data.repository.MessageRepository
import com.bryanguerra.listmessagesportfolio.ui.login.LoginScreen
import com.bryanguerra.listmessagesportfolio.ui.login.LoginViewModel
import com.bryanguerra.listmessagesportfolio.ui.login.LoginViewModelFactory
import com.bryanguerra.listmessagesportfolio.ui.messages.MessageListScreen
import com.bryanguerra.listmessagesportfolio.ui.messages.MessageViewModelFactory
import com.bryanguerra.listmessagesportfolio.ui.messages.MessagesViewModel
import com.bryanguerra.listmessagesportfolio.ui.theme.AppTheme
import com.bryanguerra.listmessagesportfolio.utils.UserPreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = UserPreferences
        val repository = MessageRepository(RetrofitClient.apiService)

        setContent {
            val navController = rememberNavController()
            var isLoggedIn by remember { mutableStateOf(false) }

            // Revisión síncrona del token (simplificada)
            LaunchedEffect(Unit) {
                userPreferences.accessToken(context = this@MainActivity).collect { token ->
                    isLoggedIn = !token.isNullOrBlank()
                }
            }
            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "messages" else "login"
                ) {

                    composable("login") {
                        val loginViewModel: LoginViewModel =
                            viewModel(factory = LoginViewModelFactory(application, repository))
                        LoginScreen(
                            loginViewModel = loginViewModel,
                            navController = navController
                        )
                    }
                    composable("messages") {
                        MessageListScreen(
                            messagesViewModel = MessageViewModelFactory(
                                application,
                                repository
                            ).create(MessagesViewModel::class.java),
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}
