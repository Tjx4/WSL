package co.za.rain.myapplication.models

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("timezone") var timezone: String?,
    @SerializedName("current") var current: Current?
)