package com.bcsbattle.weatherapp.Network.repository

import android.annotation.SuppressLint
import android.location.Geocoder
import com.bcsbattle.weatherapp.Data.CurrentLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource

class weatherDataRepository {

@SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        onSuccess: (currentLocation: CurrentLocation) -> Unit,
        onFailure: () -> Unit
    ) {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location ?: onFailure()
            onSuccess(
                CurrentLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }.addOnFailureListener { onFailure() }
    }

    @Suppress("DEPRECATION")
    fun updateAddressText(
        currentLocation : CurrentLocation,
        geocoder: Geocoder
    ) : CurrentLocation{
        val latitude = currentLocation.latitude?:return currentLocation
        val longitude = currentLocation.longitude?:return  currentLocation
        return geocoder.getFromLocation(latitude,longitude,1)?.let{
            addresses ->
            val addresses = addresses[0]
            var addressText = StringBuilder()
            addressText = java.lang.StringBuilder()
            addressText.append(addresses.locality).append(",")
            addressText.append((addresses.adminArea)).append(",")
            addressText.append(addresses.countryName)
            currentLocation.copy(
                location = addressText.toString()
            )

        }?:currentLocation
    }


}