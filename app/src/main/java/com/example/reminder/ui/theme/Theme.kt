package com.example.reminder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat


private val LightColorScheme = lightColorScheme(
    primary = reminderLightPrimary,
    onPrimary = reminderLightOnPrimary,
    primaryContainer = reminderLightPrimaryContainer,
    onPrimaryContainer = reminderLightOnPrimaryContainer,
    secondary = reminderLightSecondary,
    onSecondary = reminderLightOnSecondary,
    secondaryContainer = reminderLightSecondaryContainer,
    onSecondaryContainer = reminderLightOnSecondaryContainer,
    tertiary = reminderLightTertiary,
    onTertiary = reminderLightOnTertiary,
    tertiaryContainer = reminderLightTertiaryContainer,
    onTertiaryContainer = reminderLightOnTertiaryContainer,
    error = reminderLightError,
    onError = reminderLightOnError,
    errorContainer = reminderLightErrorContainer,
    onErrorContainer = reminderLightOnErrorContainer,
    outline = reminderLightOutline,
    background = reminderLightBackground,
    onBackground = reminderLightOnBackground,
    surface = reminderLightSurface,
    onSurface = reminderLightOnSurface,
    surfaceVariant = reminderLightSurfaceVariant,
    onSurfaceVariant = reminderLightOnSurfaceVariant,
    inverseSurface = reminderLightInverseSurface,
    inverseOnSurface = reminderLightInverseOnSurface,
    inversePrimary = reminderLightInversePrimary,
//    surfaceTint = reminderLightSurfaceTint,
//    outlineVariant = reminderLightOutlineVariant,
//    scrim = reminderLightScrim,
)


private val DarkColorScheme = darkColorScheme(
    primary = reminderDarkPrimary,
    onPrimary = reminderDarkOnPrimary,
    primaryContainer = reminderDarkPrimaryContainer,
    onPrimaryContainer = reminderDarkOnPrimaryContainer,
    secondary = reminderDarkSecondary,
    onSecondary = reminderDarkOnSecondary,
    secondaryContainer = reminderDarkSecondaryContainer,
    onSecondaryContainer = reminderDarkOnSecondaryContainer,
    tertiary = reminderDarkTertiary,
    onTertiary = reminderDarkOnTertiary,
    tertiaryContainer = reminderDarkTertiaryContainer,
    onTertiaryContainer = reminderDarkOnTertiaryContainer,
    error = reminderDarkError,
    onError = reminderDarkOnError,
    errorContainer = reminderDarkErrorContainer,
    onErrorContainer = reminderDarkOnErrorContainer,
    outline = reminderDarkOutline,
    background = reminderDarkBackground,
    onBackground = reminderDarkOnBackground,
    surface = reminderDarkSurface,
    onSurface = reminderDarkOnSurface,
    surfaceVariant = reminderDarkSurfaceVariant,
    onSurfaceVariant = reminderDarkOnSurfaceVariant,
    inverseSurface = reminderDarkInverseSurface,
    inverseOnSurface = reminderDarkInverseOnSurface,
    inversePrimary = reminderDarkInversePrimary,
//    surfaceTint = reminderDarkSurfaceTint,
//    outlineVariant = reminderDarkOutlineVariant,
//    scrim = reminderDarkScrim,
)

@Composable
fun ReminderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        LaunchedEffect(darkTheme, colorScheme) {
            (view.context as Activity).window.statusBarColor = colorScheme.primaryContainer.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
