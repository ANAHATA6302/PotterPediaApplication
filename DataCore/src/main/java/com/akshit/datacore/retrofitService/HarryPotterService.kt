package com.akshit.datacore.retrofitService

import com.akshit.datacore.model.CharacterResponse
import retrofit2.http.GET

interface HarryPotterService {
    @GET("characters")
    suspend fun getAllCharacters(): List<CharacterResponse>
}
