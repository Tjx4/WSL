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

    private val _isNoWeather: MutableLiveData<Boolean> = MutableLiveData()
    val isNoWeather: MutableLiveData<Boolean>
        get() = _isNoWeather

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

    fun getAndSetWeather() {
        _showLoading.value = true
        var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0)

        ioScope.launch {
            var weather = getWeather(currentCoordinates)

            uiScope.launch {
                if(weather != null){
                    _weather.value = weather
                    _description.value = weather?.current?.weather?.get(0)?.description ?: ""
                }
                else{
                    _isNoWeather.value = true
                }
            }
        }
    }

    private suspend fun getWeather(currentCoordinates: LatLng) = weatherRepository.getWeatherAsync(API_KEY, currentCoordinates)
}