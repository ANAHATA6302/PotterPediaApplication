package com.akshit.datacore

import com.akshit.datacore.model.CharacterResponse
import org.junit.Assert
import org.junit.Test
import kotlinx.coroutines.test.runTest

class CharacterResponseTest {

    private val characterResponse = CharacterResponse(
        id = "id",
        name = "name",
        actor = "actor",
        image = "image",
        species = "human"
    )

    @Test
    fun `Given response is not empty, When toCharacterData is called, data is transformed correctly`() =
        runTest {
            val outPut = characterResponse.toCharacterData()
            if (outPut != null) {
                Assert.assertEquals("id", outPut.id)
                Assert.assertEquals("name", outPut.name)
                Assert.assertEquals("actor", outPut.actorName)
                Assert.assertEquals("image", outPut.imageUrl)
                Assert.assertEquals("human", outPut.species)
            } else {
                Assert.fail()
            }
        }

}
