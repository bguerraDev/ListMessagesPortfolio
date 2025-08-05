package com.bryanguerra.listmessagesportfolio.data.network

import com.bryanguerra.listmessagesportfolio.data.domain.model.LoginRequest
import com.bryanguerra.listmessagesportfolio.data.domain.model.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginResponse(
    val access: String,
    val refresh: String
)

interface ApiService {

    @POST("api/login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/messages/")
    suspend fun getMessages(
        @Header("Authorization") authHeader: String
    ): List<Message>
}