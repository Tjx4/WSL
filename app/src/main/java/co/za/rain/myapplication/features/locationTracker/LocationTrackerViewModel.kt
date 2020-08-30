package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.tables.LocationsTable
import co.za.rain.myapplication.extensions.latLngToString
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

    private val _locationSaved: MutableLiveData<Boolean> = MutableLiveData()
    val locationSaved: MutableLiveData<Boolean>
        get() = _locationSaved

    private val _locationName: MutableLiveData<String> = MutableLiveData()
    val locationName: MutableLiveData<String>
        get() = _locationName

    private val _coordinates: MutableLiveData<LatLng> = MutableLiveData()
    val coordinates: MutableLiveData<LatLng>
        get() = _coordinates

    private val _locationDescription: MutableLiveData<String> = MutableLiveData()
    val locationDescription: MutableLiveData<String>
        get() = _locationDescription

    var busyMessage: String = ""

    private val _nolocationsMessage: MutableLiveData<String> = MutableLiveData()
    val nolocationsMessage: MutableLiveData<String>
        get() = _nolocationsMessage

    init {
    }

    fun setLocationIndx(index: Int){
        _locationIndex.value = index
    }

    fun setPositionMessage(index: Int){
        val position = index + 1
        _locationIndexMessage.value = "$position of ${_locations.value?.size  } previous locations"
    }

    fun fetchAndSetPreviouseLocations(){
        _showLoading.value = true
        busyMessage = "Fetching locations, please wait"

        ioScope.launch {
            val locations = locationTrackerRepository.getPreviousLocations()

            uiScope.launch {
                if(locations.isNotEmpty()){
                    _locations.value = locations
                    setLocationIndx(0)
                }
                else
                {
                    _nolocationsMessage.value = "No locations saved yet"
                }
            }

        }
    }

    fun setCurrentLocation(currentLocationName: String){
        _currentLocation.value = currentLocationName
    }

    fun setCurrentLocationMessage(){
        _currentLocationMessage.value = "You are currently in ${_currentLocation.value ?: "Unknown location"}"
    }

    fun setCurrentLocationCoordinated(coordinates: LatLng){
        _coordinates.value = coordinates
    }

    fun saveLocation(){
        _showLoading.value = true
        busyMessage = "Adding location, please wait"

        var dateTime = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

        var userLocation = LocationsTable()
        userLocation.name = _locationName.value
        userLocation.description = _locationDescription.value
        userLocation.coordinates = _coordinates.value?.latLngToString()
        userLocation.dateTime = dateTime

        ioScope.launch {
            val locations = locationTrackerRepository.addLocationToDb(userLocation)

            uiScope.launch {
                _locationSaved.value = true
                _locationName.value = ""
                _locationDescription.value = ""
            }

        }
    }

}

