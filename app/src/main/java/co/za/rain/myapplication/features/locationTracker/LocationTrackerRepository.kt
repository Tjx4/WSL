package co.za.rain.myapplication.features.locationTracker

import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.model.LatLng

class LocationTrackerRepository() {

    suspend fun getPreviousLocations() : List<UserLocation>{
        var dumyLocations = arrayListOf (
            UserLocation("Mabopane", "When I originate", LatLng(-25.5126819, 28.0552803), "00/00/0000 00:00"),
            UserLocation("Pheli", "Kasi yaka where Im from", LatLng(-25.7752977,28.0466761), "10/10/2020 00:00"),
            UserLocation("West park", "Last but not least", LatLng(-25.7551637, 28.1177411), "01/08/2020 00:00")
        )

        return dumyLocations
    }
}