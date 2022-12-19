package it.spaarkly.jetweatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.spaarkly.jetweatherforecastapp.screens.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }
    }

}