package it.spaarkly.jetweatherforecastapp.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.spaarkly.jetweatherforecastapp.model.Favourite
import it.spaarkly.jetweatherforecastapp.repository.WeatherDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites().distinctUntilChanged()
                .collect{
                    _favList.value = it
                }
        }
    }

    fun insertFavourite(favourite: Favourite) = viewModelScope.launch {
        repository.insertFavourite(favourite)
    }

    fun updateFavourite(favourite: Favourite) = viewModelScope.launch {
        repository.updateFavourite(favourite)
    }

    fun deleteFavourite(favourite: Favourite) = viewModelScope.launch {
        repository.deleteFavourite(favourite)
    }

}