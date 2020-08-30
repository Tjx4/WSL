package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import kotlinx.coroutines.launch

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

    private val _locationIndex: MutableLiveData<Int> = MutableLiveData()
    val locationIndex: MutableLiveData<Int>
        get() = _locationIndex

    private val _currentLocationMessage: MutableLiveData<String> = MutableLiveData()
    val currentLocationMessage: MutableLiveData<String>
        get() = _currentLocationMessage

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    var busyMessage: String = "Fetching location, please wait"


    private val _nolocationsMessage: MutableLiveData<String> = MutableLiveData()
    val nolocationsMessage: MutableLiveData<String>
        get() = _nolocationsMessage

    init {

        _currentLocationMessage.value = "Save your current location"

    }

    fun setLocationIndx(index: Int){
        _locationIndex.value = index
        val position = index + 1
        _locationIndexMessage.value = "$position of ${_locations.value?.size  } previous locations"
    }

    fun getPreviousLocations(){
        _showLoading.value = true

        ioScope.launch {
            _locations.value = locationTrackerRepository.getPreviousLocations()
        }
    }

}

