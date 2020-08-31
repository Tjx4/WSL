package co.za.rain.myapplication.features.weather

import co.za.rain.myapplication.models.Weather
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("weather") var weather: List<Weather?>,
    @SerializedName("humidity") var humidity: Int?,
    @SerializedName("clouds") var clouds: Int?
)