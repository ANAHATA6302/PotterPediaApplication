package characterInfo.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshit.datacore.CharacterRepository
import com.google.ar.core.exceptions.FatalException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InfoScreenUiState(
    val imageUrl: String? = null,
    val characterName: String? = null,
    val actorName: String? = null,
    val species: String? = null,
    val gender: String? = null,
    val house: String? = null,
    val dateOfBirth: String? = null,
    val patronus: String? = null,
    val alive: String? = null,
)

class CharacterInfoScreenViewModel (
    private val characterId: String,
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(InfoScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val dataOut = characterRepository.fetchCharacters()
                val filteredList = dataOut.filter { it?.id == characterId }
                _uiState.update {
                    it.copy(
                        imageUrl = filteredList[0]?.imageUrl,
                        characterName = filteredList[0]?.name,
                        actorName = filteredList[0]?.actorName,
                        species = filteredList[0]?.species,
                        gender = filteredList[0]?.gender,
                        house = filteredList[0]?.house,
                        dateOfBirth = filteredList[0]?.dateOfBirth,
                        patronus = filteredList[0]?.patronus,
                        alive = filteredList[0]?.alive,
                    )
                }
            } catch (e: FatalException) {
                Log.e("CharacterInfoScreenViewModel", "Error Fetching Data")
            }
        }
    }
}
