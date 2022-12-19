package it.spaarkly.jetweatherforecastapp.util

import it.spaarkly.jetweatherforecastapp.BuildConfig

object Constants {

    //API Key: https://api.openweathermap.org/data/2.5/forecast/daily?q=lisbon&appid=API_KEY&units=imperial
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = BuildConfig.apiKey

}