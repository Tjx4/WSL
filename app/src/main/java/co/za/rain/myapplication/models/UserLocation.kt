package co.za.rain.myapplication.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class UserLocation(var name: String?, var description: String?, var coordinates: LatLng, var dateTime: String)