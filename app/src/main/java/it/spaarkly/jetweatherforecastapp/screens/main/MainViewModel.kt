package it.spaarkly.jetweatherforecastapp.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.spaarkly.jetweatherforecastapp.data.DataOrException
import it.spaarkly.jetweatherforecastapp.model.Weather
import it.spaarkly.jetweatherforecastapp.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String, units: String) : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }

}