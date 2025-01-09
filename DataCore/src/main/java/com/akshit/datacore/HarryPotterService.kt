package com.akshit.datacore

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HarryPotterService {
    @GET("characters")
    suspend fun getAllCharacters(): List<CharacterResponse>
}

object RetrofitInstance {
    val apiService: HarryPotterService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HarryPotterService::class.java)
    }
}