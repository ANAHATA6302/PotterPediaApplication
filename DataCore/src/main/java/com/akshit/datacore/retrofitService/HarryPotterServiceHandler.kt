package com.akshit.datacore.retrofitService

import com.akshit.datacore.model.Character
import com.google.ar.core.exceptions.FatalException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HarryPotterServiceHandler(
    private val service: HarryPotterService
) {
    suspend fun execute(): List<Character?> {
        return withContext(Dispatchers.IO) {
            try {
                service.getAllCharacters().map {
                    it.toCharacterData()
                }
            } catch (e: Exception) {
                throw FatalException()
            }

        }
    }
}
