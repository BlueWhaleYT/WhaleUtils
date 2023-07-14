package com.bluewhaleyt.compose_common.image.extension

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource

@Composable
fun @receiver:DrawableRes Int.toPainter(): Painter {
    return ImageVector.vectorResource(this).toPainter()
}

@Composable
fun ImageVector.toPainter(): Painter {
    return rememberVectorPainter(this)
}