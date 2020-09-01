package co.za.rain.myapplication.features.weather

import co.za.rain.myapplication.helpers.RetrofitHelper
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng

class WeatherRepository(private var retrofitHelper: RetrofitHelper) {
    suspend fun getWeatherAsync(apiKey: String, latLng: LatLng) : WeatherModel? {
        return try {
            retrofitHelper.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)
        } catch (ex: Exception){
            WeatherModel("", null)
        }
    }
}