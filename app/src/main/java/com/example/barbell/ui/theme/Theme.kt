package com.example.barbell.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimaryDark,
    secondary = BrandSecondaryDark,
    tertiary = BrandAccentDark,
    background = BrandDark,
    surface = BrandSurfaceDark,
    onBackground = BrandTextDark,
    onSurface = BrandTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimaryLight,
    secondary = BrandSecondaryLight,
    tertiary = BrandAccentLight,
    background = BrandLight,
    surface = BrandSurfaceLight,
    onBackground = BrandTextLight,
    onSurface = BrandTextLight
)

@Composable
fun BarbellTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
        content = content
    )
}