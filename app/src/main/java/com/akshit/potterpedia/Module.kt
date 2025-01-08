package com.akshit.potterpedia

import com.akshit.datacore.CharacterRepository
import landingPage.presentation.LandingScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Provide SharedPreferencesHelper
    single { CharacterRepository(androidContext()) }

    viewModel { LandingScreenViewModel(get()) }
}