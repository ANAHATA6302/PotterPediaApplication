package landingPage.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshit.datacore.model.Character
import com.akshit.datacore.CharacterRepository
import com.google.ar.core.exceptions.FatalException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LandingScreenUiState(
    val characterList: List<Character?> = emptyList(),
    val isListLoaded: Boolean = false,
)

class LandingScreenViewModel (
    private val characterRepository: CharacterRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LandingScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun updateCache() {
        viewModelScope.launch {
            try {
                characterRepository.updateCache()
                fetchData()
            } catch (e: FatalException) {
                Log.e("LandingScreenViewModel", "Error Updating Data")
            }
        }
    }
    init {
        viewModelScope.launch {
            fetchData()
        }
    }
    private fun fetchData() {
        viewModelScope.launch {
            try {
                val dataOut = characterRepository.fetchCharacters()
                _uiState.update {
                    it.copy(
                        characterList = dataOut,
                        isListLoaded = dataOut.isNotEmpty()
                    )
                }
            } catch (e: FatalException) {
                Log.e("LandingScreenViewModel", "Error Fetching Data")
            }
        }
    }
}
