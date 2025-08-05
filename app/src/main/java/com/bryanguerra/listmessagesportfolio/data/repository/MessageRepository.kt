package com.bryanguerra.listmessagesportfolio.data.repository

import com.bryanguerra.listmessagesportfolio.data.domain.model.LoginRequest
import com.bryanguerra.listmessagesportfolio.data.domain.model.Message
import com.bryanguerra.listmessagesportfolio.data.network.ApiService
import com.bryanguerra.listmessagesportfolio.data.network.LoginResponse

class MessageRepository(
    private val apiService: ApiService
) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    suspend fun getMessages(authToken: String): List<Message> {
        return apiService.getMessages("Bearer $authToken")
    }
}