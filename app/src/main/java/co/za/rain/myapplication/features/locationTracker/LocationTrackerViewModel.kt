package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.model.LatLng

class LocationTrackerViewModel(application: Application, private val locationTrackerRepository: LocationTrackerRepository) : BaseVieModel(application) {

    private val _currentLocation: MutableLiveData<String> = MutableLiveData()
    val currentLocation: MutableLiveData<String>
        get() = _currentLocation

    private val _locations: MutableLiveData<List<UserLocation>> = MutableLiveData()
    val locations: MutableLiveData<List<UserLocation>>
        get() = _locations

    private val _locationIndexMessage: MutableLiveData<String> = MutableLiveData()
    val locationIndexMessage: MutableLiveData<String>
        get() = _locationIndexMessage

    private val _currentLocationMessage: MutableLiveData<String> = MutableLiveData()
    val currentLocationMessage: MutableLiveData<String>
        get() = _currentLocationMessage

    init {
        var dumyLocations = arrayListOf (
            UserLocation("Mabopane", "When I originate", LatLng(0.0, 0.0), "00/00/0000 00:00"),
            UserLocation("Pheli", "Kasi yaka where Im from", LatLng(0.0, 0.0), "10/10/2020 00:00"),
            UserLocation("West park", "Last but not least", LatLng(0.0, 0.0), "01/08/2020 00:00")
        )

        _currentLocation.value = "Sunny side"
        _currentLocationMessage.value = "Save your current location" //You are currently at ${_currentLocation.value}"
        _locations.value = dumyLocations
        setLocationIndx(1)
    }

    fun setLocationIndx(indx: Int){
        _locationIndexMessage.value = "$indx of ${_locations.value?.size  } previous locations"
    }

}

