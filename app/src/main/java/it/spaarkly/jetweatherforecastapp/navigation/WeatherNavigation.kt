package it.spaarkly.jetweatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import it.spaarkly.jetweatherforecastapp.screens.main.MainScreen
import it.spaarkly.jetweatherforecastapp.screens.main.MainViewModel
import it.spaarkly.jetweatherforecastapp.screens.search.SearchScreen
import it.spaarkly.jetweatherforecastapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(route = "$route/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            })) {navBack ->
            navBack.arguments?.getString("city" ).let {city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController, mainViewModel, city = city!!)
            }
        }
        composable(route = WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }

}