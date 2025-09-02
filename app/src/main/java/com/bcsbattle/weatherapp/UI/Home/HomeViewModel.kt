package com.bcsbattle.weatherapp.UI.Home

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcsbattle.weatherapp.Data.CurrentLocation
import com.bcsbattle.weatherapp.Network.repository.weatherDataRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class HomeViewModel(private val  weatherDataRepository: weatherDataRepository) : ViewModel(){

    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        geocoder: Geocoder
    ){
        viewModelScope.launch {
            emitCurrentLocationUistate(isLoading = true)
           weatherDataRepository.getCurrentLocation(
               fusedLocationProviderClient = fusedLocationProviderClient,
               onSuccess = {currentLocation ->
                   updateAddressText(currentLocation,geocoder)
               },
               onFailure = {
                   emitCurrentLocationUistate(error = "Unable to fetch current Location" )
               }
           )
        }
    }

    private fun updateAddressText (currentLocation: CurrentLocation,geocoder: Geocoder){
        viewModelScope.launch {
            val location = weatherDataRepository.updateAddressText(currentLocation,geocoder)
            emitCurrentLocationUistate(currentLocation=location)
        }
    }
    private val _currentLocation = MutableLiveData<CurrentLocationDataState>()
    val currentLocation : LiveData<CurrentLocationDataState>get() = _currentLocation

    private fun emitCurrentLocationUistate(
        isLoading: Boolean = false,
        currentLocation: CurrentLocation? = null,
        error : String? = null,
    ){
        val currentLocationDataState = CurrentLocationDataState(isLoading,currentLocation,error)
        _currentLocation.value = currentLocationDataState
    }

    data class CurrentLocationDataState(
        val isLoading : Boolean,
        val currentLocation: CurrentLocation?,
        val error : String?
    )
}