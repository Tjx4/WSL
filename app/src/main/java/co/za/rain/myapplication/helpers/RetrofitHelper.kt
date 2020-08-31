package co.za.rain.myapplication.helpers

import co.za.rain.myapplication.constants.LOCATION_WEATHER
import co.za.rain.myapplication.models.WeatherModel
import retrofit2.Call
import retrofit2.http.*

interface RetrofitHelper {
    @GET(LOCATION_WEATHER)
    suspend fun getMyLocationWeatherAsync(@Query("appid") appid: String, @Query("lat") lat: Double, @Query("lon") lon: Double): WeatherModel?

    @GET(LOCATION_WEATHER)
    fun getMyLocationWeather(@Query("appid") appid: String, @Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherModel>?
}