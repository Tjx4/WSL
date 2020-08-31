package co.za.rain.myapplication.features.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.rain.myapplication.helpers.MyApi
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            val retrofitHelper = MyApi.retrofitHelper
            val repository = WeatherRepository(retrofitHelper)
            return WeatherViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}