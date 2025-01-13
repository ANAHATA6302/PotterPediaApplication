package com.akshit.datacore

import android.content.Context
import com.akshit.datacore.model.Character
import com.akshit.datacore.retrofitService.HarryPotterServiceHandler
import com.google.ar.core.exceptions.FatalException
import com.google.gson.Gson

class CharacterRepository (
    context: Context,
    private val handler: HarryPotterServiceHandler,
    private val gson: Gson,
) {
    private val sharedPreferences = context.getSharedPreferences(
        PREFERENCE_KEY,
        Context.MODE_PRIVATE
    )

    /**
     * Fetches data from API
     * @throws [FatalException] if there is an error
     */
    suspend fun fetchCharacters(): List<Character?> {
        val cacheData = getCacheData()
        return cacheData.ifEmpty {
            val characters = handler.execute()
            cacheData(characters)
            characters
        }
    }

    /**
     * Updates data in the cache and fetches from API
     * @throws [FatalException] if there is an error
     */
    suspend fun updateCache() {
        try {
            val characters = handler.execute()
            if (characters.isNotEmpty()) {
                clearCache()
                cacheData(characters)
            }
        } catch (e: Exception) {
            throw FatalException()
        }
    }

   private fun cacheData(characters: List<Character?>) {
        sharedPreferences.edit().apply {
            putString(STORAGE_KEY, gson.toJson(characters))
            apply()
        }
    }

    private fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }

    private fun getCacheData(): List<Character> {
        val json = sharedPreferences.getString(STORAGE_KEY, null)
        return if (json != null) {
            gson.fromJson(json, Array<Character>::class.java).toList()
        } else {
            emptyList()
        }
    }

    companion object {
        private const val PREFERENCE_KEY = "character_cache"
        private const val STORAGE_KEY = "cached_characters"
    }
}
