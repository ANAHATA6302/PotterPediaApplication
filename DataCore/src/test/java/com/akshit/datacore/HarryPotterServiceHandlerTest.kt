package com.akshit.datacore

import com.akshit.datacore.model.CharacterResponse
import com.akshit.datacore.retrofitService.HarryPotterService
import com.akshit.datacore.retrofitService.HarryPotterServiceHandler
import com.google.ar.core.exceptions.FatalException
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class HarryPotterServiceHandlerTest {
    private val testCoroutineRule = UnconfinedTestDispatcher()
    private val characterResponse = CharacterResponse(
        id = "id",
        name = "name",
        actor = "actor",
        image = "image",
        species = "human"
    )
    private lateinit var handler: HarryPotterServiceHandler
    private var service: HarryPotterService = mockk {
        coEvery { getAllCharacters() } returns listOf(characterResponse)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineRule)
        handler = HarryPotterServiceHandler(service)
    }
    @After
    fun clear() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given handler is setUp correctly, When details are fetched, correct data is received`() {
        runTest {
           val outPut = handler.execute().first()
            if (outPut != null) {
                Assert.assertEquals("id", outPut.id)
                Assert.assertEquals("name", outPut.name)
                Assert.assertEquals("actor", outPut.actorName)
                Assert.assertEquals("human", outPut.species)
            } else {
                Assert.fail("Expected Output data, but received none")
            }
        }
    }

    @Test(expected = FatalException::class)
    fun `Given handler execute fails, When details are fetched, FatalException is thrown`() {
        service = mockk {
            coEvery { getAllCharacters() } throws FatalException()
        }
        handler = HarryPotterServiceHandler(service)
        runTest {
            handler.execute()
        }
    }
}
