package landingPage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akshit.potterpedia.R
import com.akshit.potterpedia.common.ui.chargeGlow
import com.akshit.potterpedia.ui.theme.Gryffindor
import com.akshit.potterpedia.ui.theme.Hufflepuff
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import com.akshit.potterpedia.ui.theme.Ravenclaw
import com.akshit.potterpedia.ui.theme.Slytherin
import com.akshit.potterpedia.ui.theme.gaps
import com.akshit.potterpedia.ui.theme.textSizes

@Composable
fun CharacterListItem(
    characterImage: String,
    characterName: String,
    actorName: String,
    characterHouse: String,
    species: String,
    characterId: String,
    onCLick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(gaps.xs))
            .padding(
                start = gaps.xl,
                end = gaps.xl,
                bottom = gaps.xl
            )
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(gaps.s))
            .fillMaxWidth()
            .clickable {
                onCLick(characterId)
            },
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (characterImage.isNotBlank()) {
            LoadImageFromUrl(
                url = characterImage,
                houseColor = fetchHouseColor(characterHouse)
            )
            Spacer(Modifier.width(gaps.xs))
        }
        CharacterInfo(
            alias = characterName,
            actorName = actorName,
            species = species,
        )
    }
}

@Composable
private fun CharacterInfo(
    alias: String,
    actorName: String,
    species: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(gaps.xs),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (alias.isNotBlank()) {
            Text(
                text = alias,
                color = MaterialTheme.colorScheme.primary,
                fontSize = textSizes.m,
            )
        }
        if (actorName.isNotBlank()) {
            Text(
                text = actorName,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = textSizes.s,
            )
        }
        if (species.isNotBlank()) {
            Text(
                text = species,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = textSizes.s,
            )
        }
    }
}

@Composable
private fun LoadImageFromUrl(
    url: String,
    houseColor: Color = MaterialTheme.colorScheme.surface,
) {
    Box(
       modifier = Modifier
           .padding(gaps.xxs)
           .size(80.dp)
           .chargeGlow(
               radiusPx = 200f,
               colorStops = listOf(houseColor)
           ),
        contentAlignment = Alignment.Center
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
                .clip(RoundedCornerShape(32.dp))
                .size(70.dp),

            )
    }
}

@Composable
fun fetchHouseColor(houseName: String): Color {
    return when(houseName) {
        stringResource(R.string.house_gryffindor) -> Gryffindor
        stringResource(R.string.house_slytherine) -> Slytherin
        stringResource(R.string.house_ravenclaw) -> Ravenclaw
        stringResource(R.string.house_hufflepuff) -> Hufflepuff
        else -> MaterialTheme.colorScheme.background

    }
}

@PreviewLightDark
@Composable
internal fun PreviewCharacterListItem() {
    PotterPediaTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(300.dp, 600.dp)
        ) {
            CharacterListItem(
                characterImage = "",
                actorName = "Daniel Radcliff",
                characterName = "Harry Potter",
                characterHouse = stringResource(R.string.house_gryffindor),
                characterId = "",
                species = "human",
                onCLick = {}
            )
        }

    }
}
