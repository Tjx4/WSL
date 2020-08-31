package co.za.rain.myapplication.models

import com.google.gson.annotations.SerializedName

class WeatherModel(@SerializedName("name") var name: String?, @SerializedName("temp_min") var temp_min: Double?, @SerializedName("temp_max") var temp_max: Double?)