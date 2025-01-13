package com.akshit.potterpedia

import characterInfo.presentation.CharacterInfoScreenViewModel
import com.akshit.datacore.CharacterRepository
import com.akshit.datacore.retrofitService.HarryPotterService
import com.akshit.datacore.retrofitService.HarryPotterServiceHandler
import com.akshit.datacore.retrofitService.RetrofitInstance
import com.google.gson.Gson
import landingPage.presentation.LandingScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HarryPotterService> { RetrofitInstance.apiService }
    single<HarryPotterServiceHandler> { HarryPotterServiceHandler(get()) }
    single<Gson> { Gson() }
    single { CharacterRepository(context = androidContext(), get(), get())}

    viewModel { LandingScreenViewModel(get()) }
    viewModel { CharacterInfoScreenViewModel(get(), get())}
}
