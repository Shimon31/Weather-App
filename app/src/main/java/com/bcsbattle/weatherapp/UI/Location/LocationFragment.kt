package com.bcsbattle.weatherapp.UI.Location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bcsbattle.weatherapp.R
import com.bcsbattle.weatherapp.databinding.ActivityMainBinding
import com.bcsbattle.weatherapp.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        return binding.root


    }


}