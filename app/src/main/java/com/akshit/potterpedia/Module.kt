package com.akshit.potterpedia

import characterInfo.presentation.CharacterInfoScreenViewModel
import com.akshit.datacore.CharacterRepository
import landingPage.presentation.LandingScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CharacterRepository(androidContext()) }

    viewModel { LandingScreenViewModel(get()) }
    viewModel { CharacterInfoScreenViewModel(get(), get())}
}
