package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Google Dark Theme Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = GoogleBlueLight,
    onPrimary = GoogleDarkBackground,
    primaryContainer = GoogleBlueContainerDark,
    onPrimaryContainer = GoogleBlueLight,
    secondary = GoogleGreenLight,
    onSecondary = GoogleDarkBackground,
    secondaryContainer = GoogleGreenContainerDark,
    onSecondaryContainer = GoogleGreenLight,
    tertiary = GoogleYellowLight,
    onTertiary = GoogleDarkBackground,
    background = GoogleDarkBackground,
    onBackground = GoogleGrey50,
    surface = GoogleDarkSurface,
    onSurface = GoogleGrey50,
    surfaceVariant = GoogleDarkCard,
    onSurfaceVariant = GoogleGrey400,
    outline = GoogleDarkOutline,
    outlineVariant = GoogleGrey800,
    error = GoogleRedLight,
    onError = GoogleDarkBackground
)

// Google Clean Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = GoogleBlueDark,
    onPrimary = Color.White,
    primaryContainer = GoogleBlueContainerLight,
    onPrimaryContainer = GoogleBlueDark,
    secondary = GoogleGreenDark,
    onSecondary = Color.White,
    secondaryContainer = GoogleGreenContainerLight,
    onSecondaryContainer = GoogleGreenDark,
    tertiary = GoogleYellowDark,
    onTertiary = GoogleGrey900,
    background = GoogleGrey50,
    onBackground = GoogleGrey900,
    surface = Color.White,
    onSurface = GoogleGrey900,
    surfaceVariant = GoogleGrey100,
    onSurfaceVariant = GoogleGrey700,
    outline = GoogleGrey300,
    outlineVariant = Color(0xFFE8EAED),
    error = GoogleRedDark,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use intentional Google 4-color pattern
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
