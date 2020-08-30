package co.za.rain.myapplication.extensions

import com.google.android.gms.maps.model.LatLng

fun LatLng.latLngToString() = ("${this.latitude} | ${this.longitude}")