package com.pocketdev.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8DFFD),
    onPrimaryContainer = Color(0xFF1F005D),
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFA4F8E7),
    onSecondaryContainer = Color(0xFF003731),
    tertiary = Color(0xFFFF6B6B),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD6),
    onTertiaryContainer = Color(0xFF5C0000),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFB3261E),
    onError = Color.White,
    outline = Color(0xFF79747E)
)

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3700B3),
    onPrimaryContainer = Color(0xFFE8DFFD),
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF005048),
    onSecondaryContainer = Color(0xFFA4F8E7),
    tertiary = Color(0xFFFF8A80),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF93000A),
    onTertiaryContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = Color(0xFFCF6679),
    onError = Color.Black,
    outline = Color(0xFF938F99)
)

@Composable
fun PocketDevTheme(
    darkTheme: Boolean = true, // Default to dark theme for coding
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val Typography = Typography()
