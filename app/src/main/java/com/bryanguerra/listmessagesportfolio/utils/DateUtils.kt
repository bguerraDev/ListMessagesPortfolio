package com.bryanguerra.listmessagesportfolio.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatTimestampToSpanish(dateStr: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val date = ZonedDateTime.parse(dateStr, inputFormatter)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("es", "ES"))
        date.withZoneSameInstant(java.time.ZoneId.of("Europe/Madrid")).format(formatter)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}
