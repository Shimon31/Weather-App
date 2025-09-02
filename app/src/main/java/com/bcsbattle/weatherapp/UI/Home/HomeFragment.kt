package com.bcsbattle.weatherapp.UI.Home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
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
import androidx.fragment.app.viewModels
import com.bcsbattle.weatherapp.Data.CurrentLocation
import com.bcsbattle.weatherapp.R
import com.bcsbattle.weatherapp.databinding.ActivityMainBinding
import com.bcsbattle.weatherapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import org.koin.mp.Lockable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private val geocoder by lazy { Geocoder(requireContext()) }

    private val weatherAdapter = WeatherAdapter(
        onLocationClicked = { showLocationOption() }
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

    private fun setObserver(){
     with(homeViewModel) {
         currentLocation.observe(viewLifecycleOwner) {
             val currentLocationDataState = it ?: return@observe

             if (currentLocationDataState.isLoading){
                 showLoading()
             }
             currentLocationDataState.currentLocation?.let { currentLocation ->
                 hideLoading()
                 setWeatherData(currentLocation)
             }
             currentLocationDataState.error?.let { error->
                 hideLoading()
                 Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
             }
         }
     }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWeatherDataAdapter()
        setWeatherData()
        setObserver()
    }

    private fun setWeatherDataAdapter() {
        binding.recyclerView.adapter = weatherAdapter
    }

    private fun setWeatherData(currentLocation: CurrentLocation? = null) {
        weatherAdapter.setData(data = listOf(currentLocation ?: CurrentLocation()))
    }


    private fun getCurrentLocation() {
        homeViewModel.getCurrentLocation(fusedLocationProviderClient, geocoder)
    }

    private fun isLocationPermissionGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun proceedWithCurrentLocation() {

        if (isLocationPermissionGranted()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun showLocationOption() {
        val option = arrayOf("current location", "search Manually")
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Choose Location Method")
            setItems(option) { _, which ->
                when (which) {
                    0 -> proceedWithCurrentLocation()
                }
            }
            show()
        }
    }

    private fun showLoading() {
        binding.apply {
            recyclerView.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun hideLoading() {
        binding.apply {
            recyclerView.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
