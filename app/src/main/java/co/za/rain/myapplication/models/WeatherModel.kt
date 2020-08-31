package co.za.rain.myapplication.models

import co.za.rain.myapplication.features.weather.Current
import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("timezone") var timezone: String?,
    @SerializedName("current") var current: Current?
)