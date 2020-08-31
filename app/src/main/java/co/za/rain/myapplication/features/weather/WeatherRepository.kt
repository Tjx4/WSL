package co.za.rain.myapplication.features.weather

import co.za.rain.myapplication.constants.HOST
import co.za.rain.myapplication.helpers.RetrofitHelper
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(private var retrofitHelper: RetrofitHelper) {
    suspend fun getWeather(apiKey: String, latLng: LatLng) : WeatherModel? {
        return WeatherModel("Pretoria", 12.5, 21.4) //retrofitHelper?.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)
    }
}