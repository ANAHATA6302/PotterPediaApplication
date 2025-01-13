package com.akshit.datacore

import android.content.Context
import android.content.SharedPreferences
import com.akshit.datacore.model.Character
import com.akshit.datacore.model.CharacterResponse
import com.akshit.datacore.retrofitService.HarryPotterService
import com.akshit.datacore.retrofitService.HarryPotterServiceHandler
import com.google.gson.Gson
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryTest {
    private val testCoroutineRule = UnconfinedTestDispatcher()

    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk()
    private val gson: Gson = mockk {
        coEvery { toJson(any()) } just mockk()
    }
    private val context: Context = mockk {
        every { getSharedPreferences(any(), any()) } returns sharedPreferences
    }

    private val testCharacters = listOf(
        Character("id1", "Harry", "Daniel", null, null, null, null, null, alive = "true"),
        Character("id2", "Hermione", "Emma", null, null, null, null, null, alive = "true")
    )
    private val characterResponse = CharacterResponse(
        id = "id",
        name = "name",
        actor = "actor",
        image = "image",
        species = "human"
    )

    private val testCharacter = Character(
        id = "id",
        name = "name",
        actorName = "actor",
        imageUrl = "image",
        species = "human",
        gender = null,
        dateOfBirth = null,
        house = null,
        alive = null,
        patronus = null
    )
    private var handler: HarryPotterServiceHandler = mockk {
        coEvery { execute() } returns listOf(testCharacter)
    }

    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineRule)
        every { sharedPreferences.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.clear() } returns editor
        every { editor.apply() } just Runs

        characterRepository = CharacterRepository(
            context = context,
            handler,
            gson = gson
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
