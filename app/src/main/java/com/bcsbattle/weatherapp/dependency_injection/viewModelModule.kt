package com.bcsbattle.weatherapp.dependency_injection

import com.bcsbattle.weatherapp.Network.repository.weatherDataRepository
import com.bcsbattle.weatherapp.UI.Home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel (weatherDataRepository = get ()) }
}