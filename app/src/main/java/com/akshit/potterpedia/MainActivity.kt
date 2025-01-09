package com.akshit.potterpedia

import landingPage.ui.LandingScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akshit.potterpedia.common.ui.NavigationRoutes
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            navHostController = rememberNavController()
            NavHost(navController = navHostController, startDestination = NavigationRoutes.LANDING_SCREEN.route) {
                composable(NavigationRoutes.LANDING_SCREEN.route) { LandingScreen(navHostController) }
                composable(NavigationRoutes.CHARACTER_INFO_SCREEN.route) {  }
            }
        }
    }
}


