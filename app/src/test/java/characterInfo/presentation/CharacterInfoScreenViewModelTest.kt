package characterInfo.presentation

import android.util.Log
import com.akshit.datacore.Character
import com.akshit.datacore.CharacterRepository
import com.google.ar.core.exceptions.FatalException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterInfoScreenViewModelTest {

    private val testCoroutineRule = UnconfinedTestDispatcher()

    private val charList: List<Character> = listOf(
        Character(
            CHARACTER_ID,
            CHARACTER_NAME,
            CHARACTER_ACTOR,
            null,
            null,
            null,
            null,
            null,
            alive = CHARACTER_ALIVE
        )
    )

    private lateinit var viewModel: CharacterInfoScreenViewModel
    private var characterRepository: CharacterRepository = mockk<CharacterRepository> {
        coEvery { fetchCharacters() } returns charList
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineRule)
        viewModel = CharacterInfoScreenViewModel(characterId = CHARACTER_ID, characterRepository)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }
    @After
    fun clear() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given viewModel is created, When all details are known, uiState is updated`() =
        runTest {
            verifyUiState {
                Assert.assertEquals(CHARACTER_NAME, viewModel.uiState.value.characterName)
                Assert.assertEquals(CHARACTER_ACTOR, viewModel.uiState.value.actorName)
                Assert.assertEquals(CHARACTER_ALIVE, viewModel.uiState.value.alive)
            }
        }

    @Test
    fun `Given viewModel is created, When fetching details throws error, FatalException is caught and uiState is set to default`() {
        characterRepository = mockk {
            coEvery { fetchCharacters() } throws FatalException("")
        }
        viewModel = CharacterInfoScreenViewModel(CHARACTER_ID, characterRepository)
        runTest {
            verifyUiState {
                Assert.assertNull(viewModel.uiState.value.characterName)
                Assert.assertNull(viewModel.uiState.value.actorName)
                Assert.assertNull(viewModel.uiState.value.alive)
            }
        }
    }


    private fun TestScope.verifyUiState(block: () -> Unit) {
        val job =
            launch(UnconfinedTestDispatcher()) {
                viewModel.uiState.collect()
            }
        block()
        job.cancel()
    }

    companion object {
        private const val CHARACTER_ID = "abc"
        private const val CHARACTER_NAME = "harry"
        private const val CHARACTER_ACTOR = "daniel"
        private const val CHARACTER_ALIVE = "true"
    }
}
