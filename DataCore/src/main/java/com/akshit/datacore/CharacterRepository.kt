package com.akshit.datacore

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository (
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        PREFERENCE_KEY,
        Context.MODE_PRIVATE
    )

    suspend fun fetchCharacters(): List<Character?> {
        val cacheData = getCacheData()
        // also need to add the option to rewrite data to avoid duplication
        // i.e when a new fetch is done or user manually asks a refresh of data
        return if (cacheData.isEmpty()) {
            val characters = fetchFromApi()
            cacheData(characters)
            characters
        } else {
            cacheData
        }
    }

    suspend fun updateCache(): List<Character?> {
        return if (!getCacheData().isEmpty()) {
            val data = fetchFromApi()
            sharedPreferences.edit().clear().apply()
            cacheData(characters = data)
            data
        } else {
            emptyList()
        }

    }

    // Fetch from API
    private suspend fun fetchFromApi(): List<Character?> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getAllCharacters().map {
                it.toCharacterData()
            }
        }
    }

    // Cache the fetched data in SharedPreferences
    private fun cacheData(characters: List<Character?>) {
        sharedPreferences.edit().apply {
            putString("cached_characters", Gson().toJson(characters))
            apply()
        }
    }

    private fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }

    // Get cached data
    private fun getCacheData(): List<Character> {
        val json = sharedPreferences.getString("cached_characters", null)
        return if (json != null) {
            Gson().fromJson(json, Array<Character>::class.java).toList()
        } else {
            emptyList()
        }
    }

    companion object {
        private const val PREFERENCE_KEY = "character_cache"
    }
}