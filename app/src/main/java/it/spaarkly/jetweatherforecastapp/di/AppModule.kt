package it.spaarkly.jetweatherforecastapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.spaarkly.jetweatherforecastapp.data.WeatherDao
import it.spaarkly.jetweatherforecastapp.data.WeatherDatabase
import it.spaarkly.jetweatherforecastapp.network.WeatherApi
import it.spaarkly.jetweatherforecastapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesWeatherDao(weatherDatabase: WeatherDatabase) : WeatherDao = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : WeatherDatabase
    = Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_database")
        .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideOpenWeatherApi() : WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}