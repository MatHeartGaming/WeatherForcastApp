package it.spaarkly.jetweatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import it.spaarkly.jetweatherforecastapp.model.Favourite

@Database(entities = [Favourite::class, it.spaarkly.jetweatherforecastapp.model.Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao() : WeatherDao

}