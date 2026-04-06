package com.stockstracker.mobile.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Slate900,
    onPrimary = Cream50,
    secondary = Slate700,
    onSecondary = Cream50,
    tertiary = Emerald500,
    background = Cream50,
    onBackground = Slate900,
    surface = White,
    onSurface = Slate900,
    surfaceVariant = Sand200,
    onSurfaceVariant = Slate700,
    error = Coral500
)

private val DarkColors = darkColorScheme(
    primary = Cream50,
    onPrimary = Slate900,
    secondary = Slate300,
    onSecondary = Slate900,
    tertiary = Emerald500,
    background = Slate900,
    onBackground = Cream50,
    surface = Slate700,
    onSurface = Cream50,
    surfaceVariant = Slate900,
    onSurfaceVariant = Slate300,
    error = Coral500
)

@Composable
fun StocksTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = StocksTrackerTypography,
        content = content
    )
}
