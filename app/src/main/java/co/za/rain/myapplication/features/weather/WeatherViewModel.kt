package co.za.rain.myapplication.features.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.constants.API_KEY
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
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

    private val _isNoLocation: MutableLiveData<Boolean> = MutableLiveData()
    val isNoLocation: MutableLiveData<Boolean>
        get() = _isNoLocation

    private var _weather: MutableLiveData<WeatherModel> = MutableLiveData()
    var weather: MutableLiveData<WeatherModel> = MutableLiveData()
        get() = _weather

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String>
        get() = _description

    fun checkAndSetLocation(location: UserLocation?){
        _busyMessage.value = "Fetching weather, please wait"
        _showLoading.value = true

        if(location == null){
            _isNoLocation.value = true
            return
        }
        _currentLocation.value = location
    }

    fun getLocationWeather(): LiveData<WeatherModel>?{
        var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0) //Todo: Fix
       return  weatherRepository.getWeather(API_KEY, currentCoordinates)
    }

    fun getLocationWeatherAsync() {
        _showLoading.value = true

        ioScope.launch {

            var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0) //Todo: Fix
            var weather = weatherRepository.getWeatherAsync(API_KEY, currentCoordinates)

            uiScope.launch {
                _weather.value = weather
                _description.value = weather?.current?.weather?.get(0)?.description ?: ""
            }
        }
    }
}