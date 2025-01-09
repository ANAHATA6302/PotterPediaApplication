package com.akshit.potterpedia.common.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb

private const val GLOW_BLUR_PIXELS_PERCENT = 0.3f
private const val GLOW_VERTICAL_OFFSET_PERCENT = 0.025f
private const val GLOW_START_ALPHA = 0.5f
private const val SHADOW_BLUR_PIXELS_PERCENT = 0.025f
private const val SHADOW_VERTICAL_OFFSET_PERCENT = 0.025f
private const val SHADOW_START_ALPHA = 0.25f

/**
 * The custom glow/shadow hybrid used by some charge composables
 */
internal fun Modifier.chargeGlow(colorStops: List<Color>, radiusPx: Float): Modifier =
    this.drawBehind {
        drawIntoCanvas {
            // Custom two level glow/shadow hybrid
            // Glow
            val paint = Paint()
            paint
                .asFrameworkPaint()
                .apply {
                    color = Color.Transparent.toArgb()
                    setShadowLayer(
                        this@drawBehind.size.height * GLOW_BLUR_PIXELS_PERCENT,
                        0f,
                        this@drawBehind.size.height * GLOW_VERTICAL_OFFSET_PERCENT,
                        colorStops
                            .first()
                            .copy(alpha = GLOW_START_ALPHA)
                            .toArgb(),
                    )
                }

            it.drawRoundRect(
                0f,
                0f,
                this@drawBehind.size.width,
                this@drawBehind.size.height,
                radiusPx,
                radiusPx,
                paint,
            )

            // Shadow
            paint
                .asFrameworkPaint()
                .apply {
                    color = Color.Transparent.toArgb()
                    setShadowLayer(
                        this@drawBehind.size.height * SHADOW_BLUR_PIXELS_PERCENT,
                        0f,
                        this@drawBehind.size.height * SHADOW_VERTICAL_OFFSET_PERCENT,
                        Color.Black
                            .copy(alpha = SHADOW_START_ALPHA)
                            .toArgb(),
                    )
                }

            it.drawRoundRect(
                0f,
                0f,
                this@drawBehind.size.width,
                this@drawBehind.size.height,
                radiusPx,
                radiusPx,
                paint,
            )
        }
    }