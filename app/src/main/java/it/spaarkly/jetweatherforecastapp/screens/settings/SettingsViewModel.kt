package it.spaarkly.jetweatherforecastapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.spaarkly.jetweatherforecastapp.repository.WeatherDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository : WeatherDbRepository) : ViewModel() {

    private val _unitList = MutableStateFlow<List<it.spaarkly.jetweatherforecastapp.model.Unit>>(emptyList())

    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect{
                    _unitList.value = it
                }
        }
    }

    fun insertUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    fun updateUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }

    fun deleteUnit(unit: it.spaarkly.jetweatherforecastapp.model.Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }

}