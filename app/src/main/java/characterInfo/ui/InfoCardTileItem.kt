package characterInfo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import com.akshit.potterpedia.ui.theme.gaps

@Composable
fun InfoCardTileItem(
    modifier: Modifier = Modifier,
    title: String,
    info: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = gaps.m)
            .clip(RoundedCornerShape(gaps.xs))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(gaps.s))
            .padding(gaps.xs),
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoTitle(title)
        Spacer(Modifier.width(gaps.xxs))
        CharacterInfo(info)
    }
}

@Composable
private fun InfoTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun CharacterInfo(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal),
        color = MaterialTheme.colorScheme.secondary
    )
}

@PreviewLightDark
@Composable
internal fun PreviewInfoCardTileItem() {
    PotterPediaTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(300.dp, 600.dp)
        ) {
            InfoCardTileItem(
                title = "Name:",
                info = "Harry Potter"
            )
        }

    }
}
