package com.bcsbattle.weatherapp.Data

sealed class weatherData

data class CurrentLocation(
    val date: String,
    val location: String = "Choose your Location"
) : weatherData()