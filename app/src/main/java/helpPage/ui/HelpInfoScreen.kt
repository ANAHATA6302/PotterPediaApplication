package helpPage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akshit.potterpedia.R
import com.akshit.potterpedia.common.ui.TopNavigationScaffold
import com.akshit.potterpedia.common.ui.TopNavigationTitleContentScope
import com.akshit.potterpedia.common.ui.chargeGlow
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import com.akshit.potterpedia.ui.theme.gaps
import landingPage.ui.fetchHouseColor

@Composable
fun HelpInfoScreen(
    navHostController: NavHostController
) {
    PotterPediaTheme {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            TopNavigationScaffold(
                backgroundColor = MaterialTheme.colorScheme.background,
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Title(
                            text = stringResource(R.string.help_info_title),
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
                        modifier = Modifier.size(gaps.xl),
                        onClick = {
                            navHostController.popBackStack()
                        }
                    )
                }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(gaps.xs))
                        .padding(gaps.xxs),
                    text = "Color Guide to House Identification",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                HouseColorGuideComponent(
                    houseName = stringResource(R.string.house_gryffindor)
                )
                HouseColorGuideComponent(
                    houseName = stringResource(R.string.house_slytherine)
                )
                HouseColorGuideComponent(
                    houseName = stringResource(R.string.house_ravenclaw)
                )
                HouseColorGuideComponent(
                    houseName = stringResource(R.string.house_hufflepuff)
                )
                HouseColorGuideComponent(
                    houseName = stringResource(R.string.house_unlisted)
                )
            }

        }
    }
}

@Composable
private fun HouseColorGuideComponent(
    houseName: String,
) {
    val houseColor = fetchHouseColor(houseName)
    Row(
        modifier = Modifier
            .padding(horizontal = gaps.xl)
            .padding(vertical = gaps.s)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(gaps.xs))
            .padding(gaps.xxs)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .size(40.dp)
                .chargeGlow(
                    radiusPx = 200f,
                    colorStops = listOf(houseColor)
                ),

        )
        Spacer(Modifier.width(gaps.xl))
        Text(
            modifier = Modifier.weight(1f),
            text = houseName,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
