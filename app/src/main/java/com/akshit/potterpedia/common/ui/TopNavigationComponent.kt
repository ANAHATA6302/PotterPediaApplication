package com.akshit.potterpedia.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akshit.potterpedia.R
import com.akshit.potterpedia.ui.theme.gaps

internal val TOP_NAVIGATION_MIN_HEIGHT = 58.dp

private val MARQUEE_VELOCITY = 45.dp
private const val ROLLING_ENTER_ANIMATION_DURATION = 400
private const val ROLLING_EXIT_ANIMATION_DURATION = 200

@Composable
fun TopNavigationScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    navigationIcon: @Composable TopNavigationIconContentScope.() -> Unit = {},
    title: @Composable TopNavigationTitleContentScope.() -> Unit = {},
    actions: @Composable TopNavigationIconContentScope.() -> Unit = {},

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(TOP_NAVIGATION_MIN_HEIGHT, 120.dp)
            .background(color = backgroundColor)
            .padding(
                start = gaps.xl,
                end = gaps.xl,
            )
            .windowInsetsPadding(WindowInsets.statusBars)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(gaps.s, Alignment.Start),
        ) {
            IconScopeInstance.navigationIcon()
        }

        Column(
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleScopeInstance.title()
        }

        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(gaps.s, Alignment.End),
        ) {
            IconScopeInstance.actions()
        }
    }

}

private object IconScopeInstance : TopNavigationIconContentScope

interface TopNavigationIconContentScope {

    @Composable
    fun BackButton(
        onClick: () -> Unit,
        modifier: Modifier,
    ) {
        Icon(
            modifier = modifier.size(24.dp)
                .clickable(onClick = onClick),
            painter = painterResource(id = R.drawable.ic_left_chevron_24),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "",
        )
    }

    @Composable
    fun NavigationIcon(
        @DrawableRes res: Int,
        modifier: Modifier,
        contentDescription: String,
        onClick: () -> Unit,
    ) {
        Icon(
            modifier = modifier.size(24.dp)
                .clickable(onClick = onClick),
            painter = painterResource(id = res),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "",
        )
    }

}
private object TitleScopeInstance : TopNavigationTitleContentScope

interface TopNavigationTitleContentScope {
    data class Subtitle(val text: String, val color: Color)

    @Composable
    fun Title(
        text: String,
        color: Color,
        modifier: Modifier,
    ) {
        Text(
            modifier = modifier
                .basicMarquee(velocity = MARQUEE_VELOCITY),
            text = text,
            color = color,
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun RollingSingleSubTitle(
        subtitle: Subtitle,
        modifier: Modifier,
    ) {
        AnimatedContent(targetState = subtitle, transitionSpec = {
            fadeIn() + slideInVertically(
                animationSpec = tween(ROLLING_ENTER_ANIMATION_DURATION),
                initialOffsetY = { fullHeight -> fullHeight },
            ) togetherWith fadeOut(animationSpec = tween(ROLLING_EXIT_ANIMATION_DURATION))
        }, label = "RollingSubTitleAnimation") { targetState ->
            Text(
                modifier = modifier
                    .basicMarquee(velocity = MARQUEE_VELOCITY),
                text = targetState.text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
                color = targetState.color,
                maxLines = 1,
            )
        }
    }
}
