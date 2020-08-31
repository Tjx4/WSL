package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.features.database.tables.LocationsTable
import co.za.rain.myapplication.extensions.isValidDescription
import co.za.rain.myapplication.extensions.isValidName
import co.za.rain.myapplication.extensions.latLngToString
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LocationTrackerViewModel(application: Application, private val locationTrackerRepository: LocationTrackerRepository) : BaseVieModel(application) {

    private val _currentLocation: MutableLiveData<String> = MutableLiveData()
    val currentLocation: MutableLiveData<String>
        get() = _currentLocation

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
        get() = _errorMessage

    private val _locations: MutableLiveData<List<UserLocation>> = MutableLiveData()
    val locations: MutableLiveData<List<UserLocation>>
        get() = _locations

    private val _selectedLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val selectedLocation: MutableLiveData<UserLocation>
        get() = _selectedLocation

    private val _locationIndexMessage: MutableLiveData<String> = MutableLiveData()
    val locationIndexMessage: MutableLiveData<String>
        get() = _locationIndexMessage

    private val _locationIndex: MutableLiveData<Int> = MutableLiveData()
    val locationIndex: MutableLiveData<Int>
        get() = _locationIndex

    private val _currentLocationViewPagerMessage: MutableLiveData<String> = MutableLiveData()
    val currentLocationViewPagerMessage: MutableLiveData<String>
        get() = _currentLocationViewPagerMessage

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _hideError: MutableLiveData<Boolean> = MutableLiveData()
    val hideError: MutableLiveData<Boolean>
        get() = _hideError

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
        busyMessage = "Fetching locations, please wait"
        _showLoading.value = true

        ioScope.launch {
            val locations = locationTrackerRepository.getPreviousLocations()

            //Todo: show loader in container
delay(2000)

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

    fun setCurrentLocationName(currentLocationName: String){
        _currentLocation.value = currentLocationName
    }

    fun setCurrentLocationViewpagerMessage(){
        _currentLocationViewPagerMessage.value = "You are currently in ${_currentLocation.value ?: "Unknown location"}"
    }

    fun setCurrentLocationCoordinated(coordinates: LatLng){
        _coordinates.value = coordinates
    }

    fun checkAndSaveLocation(){
        var name = _locationName.value ?: ""
        var description = _locationDescription.value ?: ""

        if(!checkIsValidLocationName(name)){
            errorMessage.value = "Please enter a location name"
            return
        }

        if(!checkIsValidLocationDescription(description)){
            errorMessage.value = "Please enter at least 10 characters for your description"
            return
        }

        busyMessage = "Adding location, please wait"
        _showLoading.value = true

        var location = LocationsTable()
        location.name = name
        location.description = description
        location.coordinates = _coordinates.value?.latLngToString()
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

    fun startHideErrorCountDown(){
        ioScope.launch {
            delay(3000)
            uiScope.launch {
                _hideError.value = true
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

