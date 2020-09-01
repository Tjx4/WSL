package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.database.tables.locations.LocationsTable
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.isValidDescription
import co.za.rain.myapplication.extensions.isValidName
import co.za.rain.myapplication.extensions.latLngToString
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LocationTrackerViewModel(application: Application, private val locationTrackerRepository: LocationTrackerRepository) : BaseVieModel(application) {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _busyMessage: MutableLiveData<String> = MutableLiveData()
    val busyMessage: MutableLiveData<String>
        get() = _busyMessage

    private val _currentLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val currentLocation: MutableLiveData<UserLocation>
        get() = _currentLocation

    private val _selectedLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val selectedLocation: MutableLiveData<UserLocation>
        get() = _selectedLocation

    private val _locations: MutableLiveData<List<UserLocation>> = MutableLiveData()
    val locations: MutableLiveData<List<UserLocation>>
        get() = _locations

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
        get() = _errorMessage

    private val _locationIndexMessage: MutableLiveData<String> = MutableLiveData()
    val locationIndexMessage: MutableLiveData<String>
        get() = _locationIndexMessage

    private val _locationIndex: MutableLiveData<Int> = MutableLiveData()
    val locationIndex: MutableLiveData<Int>
        get() = _locationIndex

    private val _currentUserLocationMessage: MutableLiveData<String> = MutableLiveData()
    val currentUserLocationMessage: MutableLiveData<String>
        get() = _currentUserLocationMessage

    private val _hideError: MutableLiveData<Boolean> = MutableLiveData()
    val hideError: MutableLiveData<Boolean>
        get() = _hideError

    private val _locationSaved: MutableLiveData<Boolean> = MutableLiveData()
    val locationSaved: MutableLiveData<Boolean>
        get() = _locationSaved

    private val _locationName: MutableLiveData<String> = MutableLiveData()
    val locationName: MutableLiveData<String>
        get() = _locationName

    private val _locationDescription: MutableLiveData<String> = MutableLiveData()
    val locationDescription: MutableLiveData<String>
        get() = _locationDescription

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
        _busyMessage.value = app.getString(R.string.fetching_locations)
        _showLoading.value = true

        ioScope.launch {
            val locations = locationTrackerRepository.getPreviousLocations()

            uiScope.launch {
                if(locations.isNotEmpty()){
                    _locations.value = locations
                    setLocationIndx(0)
                }
                else
                {
                    _nolocationsMessage.value = app.getString(R.string.no_locations)
                }
            }
        }
    }

    fun setCurrentLocation(userLocation: UserLocation){
        _currentLocation.value = userLocation
        _currentLocation.value?.coordinates = LatLng(userLocation.coordinates?.latitude ?: 0.0, userLocation.coordinates?.longitude ?: 0.0)
    }

    fun setUserCurrentLocationMessage(){
        _currentUserLocationMessage.value = "You are currently in ${_currentLocation.value?.name ?: "Unknown location"}"
    }

    fun checkAndSaveLocation(){
        var name = _locationName.value ?: ""
        var description = _locationDescription.value ?: ""

        if(!checkIsValidLocationName(name)){
            errorMessage.value = app.getString(R.string.name_auth_message)
            return
        }

        if(!checkIsValidLocationDescription(description)){
            errorMessage.value = app.getString(R.string.desscription_auth_message)
            return
        }

        _busyMessage.value  = app.getString(R.string.adding_location)
        _showLoading.value = true

        var location = LocationsTable()
        location.name = name
        location.description = description
        location.coordinates = _currentLocation.value?.coordinates?.latLngToString()
        location.dateTime = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

        ioScope.launch {
            saveUserLocation(location)

            uiScope.launch {
                _locationSaved.value = true
                _locationName.value = ""
                _locationDescription.value = ""
            }
        }
    }

    fun setSelectedLocation(position: Int){
       _selectedLocation.value = _locations?.value?.get(position)
    }

    suspend fun saveUserLocation(location: LocationsTable){
        locationTrackerRepository.addLocationToDb(location)
    }

    fun checkIsValidLocationName(name: String): Boolean {
        return name.isValidName()
    }

    fun checkIsValidLocationDescription(description: String): Boolean {
        return description.isValidDescription()
    }
}