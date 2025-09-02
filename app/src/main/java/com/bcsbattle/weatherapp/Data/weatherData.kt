package com.bcsbattle.weatherapp.Data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class weatherData

data class CurrentLocation(
    val date: String = getCurrentDate(),
    val location: String = "Choose your Location",
    val latitude : Double? = null,
    val longitude : Double? = null
) : weatherData()

private fun getCurrentDate(): String {
    val currentDate = Date()
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return "Today, ${formatter.format(currentDate)} "
}