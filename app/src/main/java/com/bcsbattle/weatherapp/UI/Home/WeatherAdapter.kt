package com.bcsbattle.weatherapp.UI.Home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bcsbattle.weatherapp.Data.CurrentLocation
import com.bcsbattle.weatherapp.Data.weatherData
import com.bcsbattle.weatherapp.databinding.ContainerCurrentLocationBinding
import kotlinx.coroutines.currentCoroutineContext

class WeatherAdapter(private val onLocationClicked: () -> Unit) :
    RecyclerView.Adapter<WeatherAdapter.CurrentLocationViewHolder>() {


    private val weatherData = mutableListOf<weatherData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<weatherData>){
        weatherData.clear()
        weatherData.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentLocationViewHolder {
        return CurrentLocationViewHolder(
            ContainerCurrentLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrentLocationViewHolder, position: Int) {
        holder.bind(weatherData[position] as CurrentLocation)
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class CurrentLocationViewHolder(
        private val binding: ContainerCurrentLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentLocation: CurrentLocation) {

            with(binding) {

                currentDate.text = currentLocation.date
                textCurrentLocation.text = currentLocation.location
                myCurrentLocation.setOnClickListener { onLocationClicked() }
                textCurrentLocation.setOnClickListener { onLocationClicked() }


            }

        }

    }

}