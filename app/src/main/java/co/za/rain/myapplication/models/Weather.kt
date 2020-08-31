package co.za.rain.myapplication.models

import com.google.gson.annotations.SerializedName

class Weather (
    @SerializedName("description") var description: String?
)