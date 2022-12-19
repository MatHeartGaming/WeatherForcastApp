package it.spaarkly.jetweatherforecastapp.screens.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import it.spaarkly.jetweatherforecastapp.data.DataOrException
import it.spaarkly.jetweatherforecastapp.model.Weather

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = "Montescaglioso")
    }.value

    if(weatherData.loading == true) {
        CircularProgressIndicator(modifier = Modifier.size(60.dp))
    } else if(weatherData.data != null) {
        Text("Main Screen ${weatherData.data!!.city.country}")
    }

}