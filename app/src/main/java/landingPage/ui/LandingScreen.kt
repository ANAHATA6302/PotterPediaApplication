package landingPage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akshit.potterpedia.ui.theme.PotterPediaTheme
import landingPage.presentation.LandingScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LandingScreen(
    navHostController: NavHostController
) {
    val viewModel: LandingScreenViewModel = koinViewModel()
    viewModel.fetchData()
    PotterPediaTheme {
        Scaffold(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .size(300.dp, 600.dp)
    ) {
        Text(
            text = "Hello $name!",
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
        )
    }

}

@PreviewLightDark()
@Composable
fun GreetingPreview() {
    PotterPediaTheme {
        Greeting("Android")
    }
}