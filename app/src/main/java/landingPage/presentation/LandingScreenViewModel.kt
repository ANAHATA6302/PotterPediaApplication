package landingPage.presentation

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshit.datacore.Character
import com.akshit.datacore.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LandingScreenUiState(
    val characterList: List<Character?> = emptyList(),
    val imageUrl: String = "",
    val characterName: String = "",
    val actorName: String = ""
)

class LandingScreenViewModel (
    private val characterRepository: CharacterRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LandingScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch {
            characterRepository.updateCache()
            val dataOut = characterRepository.fetchCharacters()
            _uiState.update {
                it.copy(characterList = dataOut)
            }
            dataOut.forEach {
                println("Akshit - "+ it?.name)
                println("Akshit - "+ it?.actorName)
                println("Akshit - "+ it?.imageUrl)
                println("Akshit - "+ it?.gender)
                println("Akshit - "+ it?.alive)
                println("Akshit - "+ it?.species)
                println("Akshit - "+ it?.house)
                println("Akshit - "+ it?.dateOfBirth)
                println("Akshit - "+ it?.patronus)
                println("Akshit - "+ it?.actorName)
            }
        }
    }
}