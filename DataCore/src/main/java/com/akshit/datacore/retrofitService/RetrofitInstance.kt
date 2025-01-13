package com.akshit.datacore.retrofitService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val apiService: HarryPotterService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HarryPotterService::class.java)
    }
}
