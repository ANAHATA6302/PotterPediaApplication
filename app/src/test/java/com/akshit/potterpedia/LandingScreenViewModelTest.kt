package com.akshit.potterpedia

import android.util.Log

import com.akshit.datacore.Character
import com.akshit.datacore.CharacterRepository
import com.google.ar.core.exceptions.FatalException
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import landingPage.presentation.LandingScreenViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class LandingScreenViewModelTest {
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

    private lateinit var viewModel: LandingScreenViewModel
    private var characterRepository: CharacterRepository = mockk<CharacterRepository> {
        coEvery { fetchCharacters() } returns charList
        coEvery { updateCache() } just Runs
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineRule)
        viewModel = LandingScreenViewModel(characterRepository)
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
                Assert.assertEquals(charList, viewModel.uiState.value.characterList)
            }
        }

    @Test
    fun `Given viewModel is created, When updateCache is called, we check correct call to DataCore is made`() =
        runTest {
            viewModel.updateCache()
            coVerify(exactly = 1) { characterRepository.updateCache() }
            coVerify(exactly = 2) { characterRepository.fetchCharacters() }
        }

    @Test
    fun `Given viewModel is created, When fetching details throws error, FatalException is caught and uiState is set to default`() {
        characterRepository = mockk {
            coEvery { fetchCharacters() } throws FatalException("")
            coEvery { updateCache() } just Runs
        }
        viewModel = LandingScreenViewModel(characterRepository)
        runTest {
            verifyUiState {
                Assert.assertTrue(viewModel.uiState.value.characterList.isEmpty())
            }
        }
    }

    @Test
    fun `Given viewModel is created, When all details are empty, uiState for loading is updated to false`() {
        characterRepository = mockk {
            coEvery { fetchCharacters() } returns emptyList()
            coEvery { updateCache() } just Runs
        }
        viewModel = LandingScreenViewModel(characterRepository)
        runTest {
            verifyUiState {
                Assert.assertFalse(viewModel.uiState.value.isListLoaded)
            }
        }
    }


    @Test
    fun `Given viewModel is created, When all details are known, uiState for loading is updated to true`() =
        runTest {
            verifyUiState {
                Assert.assertTrue(viewModel.uiState.value.isListLoaded)
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
