package com.akshit.potterpedia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    secondary = Beige,
    tertiary = Bronze,
    background = Black,
    surface = DarkGrey,


)

private val LightColorScheme = lightColorScheme(
    primary = Black,
    secondary = DarkGrey,
    tertiary = NavyBlue,
    background = Beige,
    surface = Ivory,
)

@Composable
fun PotterPediaTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if(isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val gaps = Gaps(
    8.dp,
    12.dp,
    16.dp,
    18.dp,
    20.dp,
    24.dp,
    28.dp
)

data class Gaps(
    val xxs: Dp,
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val xxl: Dp,
)

data class TextSizes(
    val xxs: TextUnit = 8.sp,
    val xs: TextUnit = 12.sp,
    val s: TextUnit = 16.sp,
    val m: TextUnit = 18.sp,
    val l: TextUnit = 20.sp,
    val xl: TextUnit = 24.sp,
    val xxl: TextUnit = 28.sp,
    val xxxl: TextUnit = 32.sp
)

val textSizes = TextSizes()
