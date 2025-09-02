package com.bcsbattle.weatherapp.dependency_injection

import com.bcsbattle.weatherapp.Network.repository.weatherDataRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule = module{
    single { weatherDataRepository() }
}