package com.bcsbattle.weatherapp.UI.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bcsbattle.weatherapp.R
import com.bcsbattle.weatherapp.databinding.ActivityMainBinding
import com.bcsbattle.weatherapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
   lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


}