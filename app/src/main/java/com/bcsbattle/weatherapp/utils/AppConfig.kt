package com.bcsbattle.weatherapp.utils

import android.app.Application
import com.bcsbattle.weatherapp.dependency_injection.repositoryModule
import com.bcsbattle.weatherapp.dependency_injection.viewModelModule
import org.koin.core.context.startKoin

class AppConfig : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(listOf(repositoryModule, viewModelModule)) }
    }
}