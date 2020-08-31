package co.za.rain.myapplication.extensions

import com.google.android.gms.maps.model.LatLng
import java.lang.Exception
import java.util.regex.Pattern

fun String.stringToLatLong() : LatLng {
    return try{
        val ltLnStr = this.split(",")
        LatLng(ltLnStr[0].toDouble(), ltLnStr[1].toDouble())
    }catch (e: Exception){
        LatLng(0.0, 0.0)
    }
}

fun String.isValidName(): Boolean = this.isNotEmpty()
fun String.isValidDescription(): Boolean =  this.isNotEmpty() && this.length > 9

private fun evaluateRegex(valu: String, regex: String): Boolean {
    val inputStr = valu.trim { it <= ' ' }
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(inputStr)
    return matcher.matches()
}
