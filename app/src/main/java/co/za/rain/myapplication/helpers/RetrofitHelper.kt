package co.za.rain.myapplication.helpers

import co.za.rain.myapplication.constants.LOCATION_WEATHER
import co.za.rain.myapplication.models.WeatherModel
import retrofit2.http.*

interface RetrofitHelper {
    @GET(LOCATION_WEATHER)
    suspend fun getMyLocationWeather(@Query("key") api_key: String?, @Query("latitude") latitude: Double?, @Query("longitude") longitude: Double?): WeatherModel?
}