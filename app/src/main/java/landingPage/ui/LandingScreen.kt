package landingPage.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.akshit.datacore.model.Character
import com.akshit.potterpedia.R
import com.akshit.potterpedia.common.ui.NavigationRoutes
import com.akshit.potterpedia.common.ui.TopNavigationScaffold
import com.akshit.potterpedia.common.ui.TopNavigationTitleContentScope
import com.akshit.potterpedia.common.ui.buildScreen2Route
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import com.akshit.potterpedia.ui.theme.gaps
import com.akshit.potterpedia.ui.theme.textSizes
import landingPage.presentation.LandingScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LandingScreen(
    navHostController: NavHostController
) {
    val viewModel: LandingScreenViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    PotterPediaTheme {
        Column (
            modifier = Modifier
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
        ) {
            TopNavigationScaffold(
                navigationIcon = {
                    NavigationIcon(
                        res = R.drawable.ic_refresh_24dp,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "",
                    ) {
                        viewModel.updateCache()
                    }
                },
                title = {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Title(
                           text = stringResource(R.string.navbar_title),
                           color = MaterialTheme.colorScheme.primary,
                           Modifier
                       )
                       RollingSingleSubTitle(
                           TopNavigationTitleContentScope.Subtitle(
                               text = stringResource(R.string.navbar_sub_title),
                               MaterialTheme.colorScheme.secondary
                           ),
                           Modifier.width(200.dp)
                       )
                   }
                },
                actions = {
                    NavigationIcon(
                        res = R.drawable.ic_map_32dp,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "",
                    ) {
                        navHostController.navigate(NavigationRoutes.HELP_INFO_SCREEN.route)
                    }
                }
            )
            if (uiState.isListLoaded) {
                CreateCharacterList(
                    uiState.characterList
                ) {
                    val route = buildScreen2Route(it)
                    navHostController.navigate(route)
                }
            } else {
                LoadingPage()
            }

        }
    }
}

@Composable
private fun LoadingPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "LoaderTransitionTag")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "GlowAlphaTag"
        )
        Icon(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer(alpha = alpha),
            painter = painterResource(R.drawable.ic_harry_potter_logo_100dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "",
        )
    }
}

@Composable
private fun SearchBox(
    searchQuery: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = gaps.xl)
            .wrapContentSize()
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = textSizes.s,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(gaps.xs)
                )
                .padding(gaps.m),
            decorationBox = { innerTextField ->
                if (searchQuery.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_hint),
                        fontSize = textSizes.s,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                innerTextField()
            }
        )
    }

}

@Composable
private fun CreateCharacterList(
    list: List<Character?>,
    onCLick: (String) -> Unit,
) {
    if (list.isNotEmpty()) {
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        var filteredList by remember { mutableStateOf(list) }
        LaunchedEffect(searchQuery) {
            filteredList = if (searchQuery.text.isEmpty()) {
                list
            } else {
                list.filter {
                    val aliasCheck = it?.name?.contains(searchQuery.text, ignoreCase = true)?: false
                    val nameCheck = it?.actorName?.contains(searchQuery.text, ignoreCase = true)?: false
                    aliasCheck || nameCheck
                }
            }
        }
        SearchBox(
            searchQuery = searchQuery
        ) {
            searchQuery = it
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = gaps.s)
                .fillMaxSize(),
        ) {
            if (filteredList.isNotEmpty()) {
                items(filteredList) {
                    if (it!= null) {
                        CharacterListItem(
                            characterImage = it.imageUrl?: "",
                            characterName = it.name?: "",
                            actorName = it.actorName?: "",
                            characterHouse = it.house?: "",
                            species = it.species?: "",
                            characterId = it.id,
                            onCLick = onCLick
                        )
                    }
                }
            }
        }
    }
}
