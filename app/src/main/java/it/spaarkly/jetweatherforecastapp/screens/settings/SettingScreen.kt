package it.spaarkly.jetweatherforecastapp.screens.settings

import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.spaarkly.jetweatherforecastapp.widgets.WeatherAppBar

@Composable
fun SettingScreen(navController: NavHostController, settingsViewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar() {
            WeatherAppBar(navController = navController)
        }
    }) {it
        
    }
}
