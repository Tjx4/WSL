package co.za.rain.myapplication.helpers

import co.za.rain.myapplication.constants.LOCATION_WEATHER
import co.za.rain.myapplication.models.WeatherModel
import retrofit2.http.*

interface RetrofitHelper {

    @FormUrlEncoded
    @POST(LOCATION_WEATHER)
    suspend fun registerMember(@FieldMap params: Map<String, String>): WeatherModel?

    //@GET(LOCATION_WEATHER)
    //suspend fun getAllUsers(@Header("authorization") token: String): List<WeatherModel>?

}