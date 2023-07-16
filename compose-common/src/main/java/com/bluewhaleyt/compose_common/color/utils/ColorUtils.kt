package com.bluewhaleyt.compose_common.color.utils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bluewhaleyt.common.system.utils.SDKUtils

class ColorUtils {
    companion object {

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
        fun isDynamicColorAvailable(): Boolean {
            return SDKUtils.isAtLeastSDK31
        }
    }
}

/**
 * Get the color of primary
 *
 * @return the color of primary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorPrimary: Color @Composable get() = MaterialTheme.colorScheme.primary

/**
 * Get the color of onPrimary
 *
 * @return the color of onPrimary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnPrimary: Color @Composable get() = MaterialTheme.colorScheme.onPrimary

/**
 * Get the color of primaryContainer
 *
 * @return the color of primaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorPrimaryContainer: Color @Composable get() = MaterialTheme.colorScheme.primaryContainer

/**
 * Get the color of onPrimaryContainer
 *
 * @return the color of onPrimaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnPrimaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer

/**
 * Get the color of inversePrimary
 *
 * @return the color of inversePrimary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorInversePrimary: Color @Composable get() = MaterialTheme.colorScheme.inversePrimary

/**
 * Get the color of secondary
 *
 * @return the color of secondary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorSecondary: Color @Composable get() = MaterialTheme.colorScheme.secondary

/**
 * Get the color of onSecondary
 *
 * @return the color of onSecondary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnSecondary: Color @Composable get() = MaterialTheme.colorScheme.onSecondary

/**
 * Get the color of secondaryContainer
 *
 * @return the color of secondaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorSecondaryContainer: Color @Composable get() = MaterialTheme.colorScheme.secondaryContainer

/**
 * Get the color of onSecondaryContainer
 *
 * @return the color of onSecondaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnSecondaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onSecondaryContainer

/**
 * Get the color of tertiary
 *
 * @return the color of tertiary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorTertiary: Color @Composable get() = MaterialTheme.colorScheme.tertiary

/**
 * Get the color of onTertiary
 *
 * @return the color of onTertiary if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnTertiary: Color @Composable get() = MaterialTheme.colorScheme.onTertiary

/**
 * Get the color of tertiaryContainer
 *
 * @return the color of tertiaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorTertiaryContainer: Color @Composable get() = MaterialTheme.colorScheme.tertiaryContainer

/**
 * Get the color of onTertiaryContainer
 *
 * @return the color of onTertiaryContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnTertiaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onTertiaryContainer

/**
 * Get the color of error
 *
 * @return the color of error if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorError: Color @Composable get() = MaterialTheme.colorScheme.error

/**
 * Get the color of onError
 *
 * @return the color of onError if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnError: Color @Composable get() = MaterialTheme.colorScheme.onError

/**
 * Get the color of errorContainer
 *
 * @return the color of errorContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorErrorContainer: Color @Composable get() = MaterialTheme.colorScheme.errorContainer

/**
 * Get the color of onErrorContainer
 *
 * @return the color of onErrorContainer if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnErrorContainer: Color @Composable get() = MaterialTheme.colorScheme.onErrorContainer

/**
 * Get the color of background
 *
 * @return the color of background if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorBackground: Color @Composable get() = MaterialTheme.colorScheme.background

/**
 * Get the color of onBackground
 *
 * @return the color of onBackground if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnBackground: Color @Composable get() = MaterialTheme.colorScheme.onBackground

/**
 * Get the color of surface
 *
 * @return the color of surface if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorSurface: Color @Composable get() = MaterialTheme.colorScheme.surface

/**
 * Get the color of onSurface
 *
 * @return the color of onSurface if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnSurface: Color @Composable get() = MaterialTheme.colorScheme.onSurface

/**
 * Get the color of surfaceVariant
 *
 * @return the color of surfaceVariant if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorSurfaceVariant: Color @Composable get() = MaterialTheme.colorScheme.surfaceVariant

/**
 * Get the color of onSurfaceVariant
 *
 * @return the color of onSurfaceVariant if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOnSurfaceVariant: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

/**
 * Get the color of surfaceTint
 *
 * @return the color of surfaceTint if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorSurfaceTint: Color @Composable get() = MaterialTheme.colorScheme.surfaceTint

/**
 * Get the color of inverseSurface
 *
 * @return the color of inverseSurface if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorInverseSurface: Color @Composable get() = MaterialTheme.colorScheme.inverseSurface

/**
 * Get the color of inverseOnSurface
 *
 * @return the color of inverseOnSurface if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorInverseOnSurface: Color @Composable get() = MaterialTheme.colorScheme.inverseOnSurface

/**
 * Get the color of outline
 *
 * @return the color of outline if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOutline: Color @Composable get() = MaterialTheme.colorScheme.outline

/**
 * Get the color of outlineVariant
 *
 * @return the color of outlineVariant if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorOutlineVariant: Color @Composable get() = MaterialTheme.colorScheme.outlineVariant

/**
 * Get the color of scrim
 *
 * @return the color of scrim if it's possible
 * @see [MaterialTheme.colorScheme]
 */
val colorScrim: Color @Composable get() = MaterialTheme.colorScheme.scrim