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

    suspend fun fetchCharacters(): List<CharacterResponse> {
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

    // Fetch from API
    private suspend fun fetchFromApi(): List<CharacterResponse> {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiService.getAllCharacters()
        }
    }

//    private fun convertCharacterData(data: List<CharacterResponse>): List<Character?> {
//        return data.flatMap { response ->
//            if (response.id == null) {
//                null
//            } else {
//
//            }
//        }
//    }

    // Cache the fetched data in SharedPreferences
    private fun cacheData(characters: List<CharacterResponse>) {
        sharedPreferences.edit().apply {
            putString("cached_characters", Gson().toJson(characters))
            apply()
        }
    }

    // Get cached data
    private fun getCacheData(): List<CharacterResponse> {
        val json = sharedPreferences.getString("cached_characters", null)
        return if (json != null) {
            Gson().fromJson(json, Array<CharacterResponse>::class.java).toList()
        } else {
            emptyList()
        }
    }

    companion object {
        private const val PREFERENCE_KEY = "character_cache"
    }
}