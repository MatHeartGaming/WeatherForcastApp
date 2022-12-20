package it.spaarkly.jetweatherforecastapp.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import it.spaarkly.jetweatherforecastapp.data.DataOrException
import it.spaarkly.jetweatherforecastapp.model.Weather
import it.spaarkly.jetweatherforecastapp.model.WeatherItem
import it.spaarkly.jetweatherforecastapp.navigation.WeatherScreens
import it.spaarkly.jetweatherforecastapp.screens.settings.SettingsViewModel
import it.spaarkly.jetweatherforecastapp.util.formatDate
import it.spaarkly.jetweatherforecastapp.util.formatDecimals
import it.spaarkly.jetweatherforecastapp.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String = "Matera"
) {

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("metric")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if(!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ").first().lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = city, units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator(modifier = Modifier.size(60.dp))
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController, isImperial = isImperial)
        }
    }


}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name} , ${weather.city.country}",
            navController = navController,
            elevation = 5.dp,
            onAddActionClick = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
        )
    }) {
        it
        MainContent(data = weather, isImperial = isImperial)
    }

}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list.first().weather.first().icon}.png"
    val weatherItem = data.list[0]
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            formatDate(weatherItem.dt), style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xffFFC400)
        ) {
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text("${formatDecimals(weatherItem.temp.day)}°", style = MaterialTheme.typography.h4, fontWeight = FontWeight.ExtraBold)
                Text(weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
                
            }

        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        SunRiseSunSet(weather = weatherItem)
        Text("This week", style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold)

        Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xffeef1ef),
        shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp),
                content = {
                items(items = data.list) {item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            })
        }
    }
}