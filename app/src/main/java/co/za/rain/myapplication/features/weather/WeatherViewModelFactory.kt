package co.za.rain.myapplication.features.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            val repository = WeatherRepository()
            return WeatherViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}