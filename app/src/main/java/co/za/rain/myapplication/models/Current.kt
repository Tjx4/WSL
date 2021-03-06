package co.za.rain.myapplication.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("weather") var weather: List<Weather?>,
    @SerializedName("humidity") var humidity: Int?,
    @SerializedName("clouds") var clouds: Int?,
    @SerializedName("temp") var temp: Double?,
    @SerializedName("wind_speed") var wind_speed: Double?
)