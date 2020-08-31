package co.za.rain.myapplication.features.locationTracker

import co.za.dvt.myskilldevapp.features.database.tables.LocationsTable
import co.za.rain.myapplication.database.WSLDatabase
import co.za.rain.myapplication.extensions.stringToLatLong
import co.za.rain.myapplication.helpers.RetrofitHelper
import co.za.rain.myapplication.models.UserLocation
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng

class LocationTrackerRepository(private var database: WSLDatabase, private var retrofitHelper: RetrofitHelper) {
    suspend fun getPreviousLocations() : List<UserLocation>{
        var savedLocations =  ArrayList<UserLocation>()

        database.LOCDAO.getAllLocations()?.forEach{
            val currentLocation = UserLocation(it.name, it.description, it.coordinates?.stringToLatLong(), it.dateTime)
            savedLocations.add(currentLocation)
        }

        return savedLocations
    }

    suspend fun addLocationToDb(locationsTable: LocationsTable) {
        database.LOCDAO.insert(locationsTable)
    }

    suspend fun getWeather(apiKey: String, latLng: LatLng) : WeatherModel? {
        return retrofitHelper?.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)
    }
}