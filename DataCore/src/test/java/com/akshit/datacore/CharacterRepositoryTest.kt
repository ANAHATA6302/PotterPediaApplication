package com.akshit.datacore

import org.junit.Before
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryTest {
    private val testCoroutineRule = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineRule)
    }

}
