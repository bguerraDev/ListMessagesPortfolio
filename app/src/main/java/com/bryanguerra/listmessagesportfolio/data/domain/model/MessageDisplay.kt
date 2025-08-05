package com.bryanguerra.listmessagesportfolio.data.domain.model

data class MessageDisplay(
    val id: String,
    val name: String,
    val email: String,
    val message: String,
    val timestamp: String,
    val formattedDate: String // ← nuevo campo para mostrar y filtrar
)