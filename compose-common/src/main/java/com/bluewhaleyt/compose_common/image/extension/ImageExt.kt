package com.bluewhaleyt.compose_common.image.extension

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource

/**
 * Convert drawable resource id [Int] to [Painter].
 *
 * @return the painter of given drawable resource id
 * @see [ImageVector.toPainter]
 * @receiver [DrawableRes]
 *
 * #### Example
 * ```kt
 * @Composable
 * fun App() {
 *     Icon(
 *         painter = R.drawable.ic_custom_whale.toPainter(),
 *         contentDescription = null
 *     )
 * }
 * ```
 */
@Composable
fun @receiver:DrawableRes Int.toPainter(): Painter {
    return ImageVector.vectorResource(this).toPainter()
}

/**
 * Convert [ImageVector] to [Painter].
 *
 * @return the image vector of given painter
 */
@Composable
fun ImageVector.toPainter(): Painter {
    return rememberVectorPainter(this)
}