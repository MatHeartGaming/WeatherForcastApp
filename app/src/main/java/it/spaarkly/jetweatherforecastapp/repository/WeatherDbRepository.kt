package it.spaarkly.jetweatherforecastapp.repository

import it.spaarkly.jetweatherforecastapp.data.WeatherDao
import it.spaarkly.jetweatherforecastapp.model.Favourite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavourites() : Flow<List<Favourite>> = weatherDao.getFavourite()

    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavourite(favourite)

    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite)

    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite)

    suspend fun deleteAllFavourites() = weatherDao.deleteAllFavourites()

    suspend fun getFavById(city: String) = weatherDao.getFavById(city)

    fun getUnits() : Flow<List<it.spaarkly.jetweatherforecastapp.model.Unit>> = weatherDao.getUnits()

    suspend fun insertUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = weatherDao.insertUnit(unit)

    suspend fun updateUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = weatherDao.updateUnit(unit)

    suspend fun deleteUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = weatherDao.deleteUnit(unit)

}