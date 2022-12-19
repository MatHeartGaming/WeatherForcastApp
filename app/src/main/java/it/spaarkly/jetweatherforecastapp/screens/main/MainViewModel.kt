package it.spaarkly.jetweatherforecastapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.spaarkly.jetweatherforecastapp.data.DataOrException
import it.spaarkly.jetweatherforecastapp.model.Weather
import it.spaarkly.jetweatherforecastapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city : String) : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }

}