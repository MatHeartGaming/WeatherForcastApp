package it.spaarkly.jetweatherforecastapp.data

import androidx.room.*
import it.spaarkly.jetweatherforecastapp.model.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM fav_tbl")
    fun getFavourite() : Flow<List<Favourite>>

    @Query("SELECT * FROM fav_tbl WHERE city = :city")
    suspend fun getFavById(city : String) : Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: Favourite)

    @Query("DELETE FROM fav_tbl")
    suspend fun deleteAllFavourites()

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("SELECT * FROM settings_tbl")
    fun getUnits() : Flow<List<it.spaarkly.jetweatherforecastapp.model.Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit)

    @Delete
    suspend fun deleteUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit)

}