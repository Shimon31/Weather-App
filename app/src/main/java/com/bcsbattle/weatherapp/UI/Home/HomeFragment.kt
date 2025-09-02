package com.bcsbattle.weatherapp.UI.Home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bcsbattle.weatherapp.Data.CurrentLocation
import com.bcsbattle.weatherapp.R
import com.bcsbattle.weatherapp.databinding.ActivityMainBinding
import com.bcsbattle.weatherapp.databinding.FragmentHomeBinding
import org.koin.mp.Lockable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private val weatherAdapter = WeatherAdapter(
        onLocationClicked = {showLocationOption()}
    )

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherDataAdapter()
        setWeatherData()
    }

    private fun setWeatherDataAdapter() {
        binding.recyclerView.adapter = weatherAdapter
    }

    private fun setWeatherData() {
        weatherAdapter.setData(data = listOf(CurrentLocation()))
    }



    private fun getCurrentLocation() {
        Toast.makeText(requireContext(), "GetCurrentLocation", Toast.LENGTH_SHORT).show()
    }

    private fun isLocationPermissionGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission(){
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun proceedWithCurrentLocation(){

        if (isLocationPermissionGranted()){
            getCurrentLocation()
        }else{
            requestLocationPermission()
        }
    }

    private fun showLocationOption(){
        val option = arrayOf("current location","search Manually")
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Choose Location Method")
            setItems(option){_,which ->
                when(which){
                    0 ->proceedWithCurrentLocation()
                }
            }
            show()
        }
    }
}