package it.spaarkly.jetweatherforecastapp.repository

import android.util.Log
import it.spaarkly.jetweatherforecastapp.data.DataOrException
import it.spaarkly.jetweatherforecastapp.model.Weather
import it.spaarkly.jetweatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String) : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (exc : Exception) {
            Log.d("REX", "getWeather: ${exc.localizedMessage}")
            return DataOrException(e = exc)
        }
        Log.d("REX", "getWeather 2: ${response.city.country}")
        return DataOrException(data = response, loading = false)
    }

}