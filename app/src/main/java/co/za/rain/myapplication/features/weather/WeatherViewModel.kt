package co.za.rain.myapplication.features.weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.constants.API_KEY
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _busyMessage: MutableLiveData<String> = MutableLiveData()
    val busyMessage: MutableLiveData<String>
        get() = _busyMessage

    private val _currentLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val currentLocation: MutableLiveData<UserLocation>
        get() = _currentLocation

    private var _weather: MutableLiveData<WeatherModel> = MutableLiveData()
    var weather: MutableLiveData<WeatherModel> = MutableLiveData()
        get() = _weather

    fun setCurrentLocation(location: UserLocation){
        _currentLocation.value = location
    }

    fun getLocationWeather(){
        var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0) //Todo: Fix
        ioScope.launch {
            val weather = weatherRepository.getWeather(API_KEY, currentCoordinates)

            uiScope.launch {
                _weather.value = weather
            }

        }
    }
}