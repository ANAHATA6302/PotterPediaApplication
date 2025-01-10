package characterInfo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import characterInfo.presentation.CharacterInfoScreenViewModel
import characterInfo.presentation.InfoScreenUiState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akshit.potterpedia.R
import com.akshit.potterpedia.common.ui.TopNavigationScaffold
import com.akshit.potterpedia.common.ui.TopNavigationTitleContentScope
import com.akshit.potterpedia.common.ui.chargeGlow
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import com.akshit.potterpedia.ui.theme.gaps
import landingPage.ui.fetchHouseColor
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CharacterInfoScreen(
    navHostController: NavHostController,
    characterId: String,
) {
    val viewModel = koinViewModel<CharacterInfoScreenViewModel> { parametersOf(characterId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PotterPediaTheme {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            TopNavigationScaffold(
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
                navigationIcon = {
                    BackButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            navHostController.popBackStack()
                        }
                    )
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                CharacterImage(url = uiState.imageUrl?: "", fetchHouseColor(uiState.house?: ""))
                Spacer(Modifier.width(gaps.s))
                CharacterName(name = uiState.characterName?: "")
            }
            BuildInfoDeck(uiState = uiState)
        }
    }
}

@Composable
private fun CharacterImage(url: String, houseColor: Color) {
    Box(
        modifier = Modifier
            .size(170.dp)
            .padding(gaps.xxs)
            .chargeGlow(
                radiusPx = 180f,
                colorStops = listOf(houseColor)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .placeholder(R.drawable.ic_place_holder_image_48dp)
                .error(R.drawable.ic_place_holder_image_48dp)
                .build(),
            contentDescription = "CharacterImage",
            modifier = Modifier
                .clip(RoundedCornerShape(45.dp))
                .size(150.dp),

            )
    }
}

@Composable
private fun CharacterName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        modifier = modifier,
        fontSize = 32.sp,
        style = MaterialTheme.typography.labelLarge.copy(
            lineHeight = 34.sp
        ),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun BuildInfoDeck(
    uiState: InfoScreenUiState
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = gaps.xl)
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = gaps.xl, vertical = gaps.m)
    ) {
        item {
            if (!uiState.actorName.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_actor_name),
                    info = uiState.actorName
                )
            }
            if (!uiState.dateOfBirth.isNullOrEmpty()) {
                // need to format it is DD-MMM-YYYY
                InfoCardTileItem(
                    title = stringResource(R.string.title_date_of_birth),
                    info = uiState.dateOfBirth
                )
            }
            if (!uiState.species.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_species),
                    info = uiState.species
                )
            }
            if (!uiState.house.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_house),
                    info = uiState.house
                )
            }
            if (!uiState.patronus.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_patronus),
                    info = uiState.patronus
                )
            }
            if (!uiState.gender.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_gender),
                    info = uiState.gender
                )
            }
            if (!uiState.alive.isNullOrEmpty()) {
                InfoCardTileItem(
                    title = stringResource(R.string.title_alive),
                    info = uiState.alive
                )
            }
        }
    }
}
