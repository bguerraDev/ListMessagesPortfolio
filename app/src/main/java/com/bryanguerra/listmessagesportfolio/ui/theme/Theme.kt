package com.bryanguerra.listmessagesportfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF006875),
    onPrimary = Color.White,
    surface = Color(0xFF000000),
    onSurface = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF62D4E3),
    onPrimary = Color.Black,
    surface = Color(0xFF000000),
    onSurface = Color.White
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}
