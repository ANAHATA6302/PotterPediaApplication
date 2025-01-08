package landingPage.presentation

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshit.datacore.CharacterRepository
import kotlinx.coroutines.launch

class LandingScreenViewModel (
    private val characterRepository: CharacterRepository
): ViewModel() {

    fun fetchData() {
        viewModelScope.launch {
            val dataOut = characterRepository.fetchCharacters()
            dataOut.forEach {
                println("Akshit - "+ it?.name)
            }
        }
    }
}