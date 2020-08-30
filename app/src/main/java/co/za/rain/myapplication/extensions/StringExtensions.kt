package co.za.rain.myapplication.extensions

import com.google.android.gms.maps.model.LatLng
import java.lang.Exception

fun String.stringToLatLong() : LatLng {
    return try{
        val ltlnStr = this.split(" | ")
        LatLng(ltlnStr[0].toDouble(), ltlnStr[1].toDouble())
    }catch (e: Exception){
        LatLng(0.0, 0.0)
    }
}