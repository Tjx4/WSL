package co.za.rain.myapplication.features.locationTracker

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import co.za.rain.myapplication.R
import co.za.rain.myapplication.adapters.LocationsAdapter
import co.za.rain.myapplication.constants.USER_MARKER_TAG
import co.za.rain.myapplication.databinding.ActivityLocationTrackerBinding
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.blinkView
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseMapActivity
import co.za.rain.myapplication.helpers.hideCurrentLoadingDialog
import co.za.rain.myapplication.helpers.showLoadingDialog
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_location_tracker.*

class LocationTrackerActivity : BaseMapActivity(), LocationsAdapter.LocationClickListener {
    private lateinit var binding: ActivityLocationTrackerBinding
    lateinit var locationTrackerViewModel: LocationTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = LocationTrackerViewModelFactory(application)

        locationTrackerViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            LocationTrackerViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_tracker)
        binding.locationTrackerViewModel = locationTrackerViewModel
        binding.lifecycleOwner = this

        addObservers()

        setTranslucentStatusBar(DEFAULT_STATUS_BAR_ALPHA)

        // Todo: fix logic
        if(isGooglePlayServicesAvailable()){
            checkLocationPermissionAndContinue()
        }
        else{
            Toast.makeText(this, "Google play services unavailable", Toast.LENGTH_LONG).show()
        }
    }


    private fun addObservers() {
        locationTrackerViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        locationTrackerViewModel.locations.observe(this, Observer { onLocationsUpdated(it) })
        locationTrackerViewModel.nolocationsMessage.observe(this, Observer { onNolocations(it) })
        locationTrackerViewModel.locationIndex.observe(this, Observer { onLocationSelected(it) })
    }

    fun onLocationSelected(position: Int) {
        val position  = airportMarkers[position].position
        goToLocationZoomAnimated(position, LOCATION_ZOOM)
    }

    fun onNolocations(message: String) {
        //llLocationsContainer.visibility = View.VISIBLE
        //lldefContainer.visibility = View.GONE
        //TODO: Add no locations message
    }

    fun onLocationsUpdated(locations: List<UserLocation>) {
        var  locationsAdapter = LocationsAdapter(this, locations)
        locationsAdapter.setLocationClickListener(this)

        val categoryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryLayoutManager.initialPrefetchItemCount = locations.size

        rvLocations?.layoutManager = categoryLayoutManager
        rvLocations?.adapter = locationsAdapter

        val snapHelper: SnapHelper = object : PagerSnapHelper() {
            override fun findTargetSnapPosition(
                layoutManager: RecyclerView.LayoutManager,
                velocityX: Int,
                velocityY: Int
            ): Int {
                var indx = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                var maxLocations = locationTrackerViewModel.locations.value?.size ?: 0
                var finalIndex = if(indx >= maxLocations) { maxLocations } else { indx }
                locationTrackerViewModel.setLocationIndx(finalIndex)
                locationTrackerViewModel.setPositionMessage(finalIndex)
                return indx
            }
        }

        snapHelper.attachToRecyclerView(rvLocations)
        plotLocationMarkers(locations)
        hideCurrentLoadingDialog(this)
    }

    override fun onServiceCategoryClick(view: View, position: Int) {

    }

    fun onMenuButtonClicked(view: View) {
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            Toast.makeText(this, "onMenuButtonClicked", Toast.LENGTH_LONG).show()
        }, {})
    }

    fun onSaveLocationButtonClicked(view: View) {
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            Toast.makeText(this, "onSaveLocationButtonClicked", Toast.LENGTH_LONG).show()
        }, {})
    }

    fun onShowLocationsButtonClicked(view: View) {
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            showLocationsRecyclerView()
        }, {})
    }

    private fun showLocationsRecyclerView() {
        llLocationsContainer.visibility = View.VISIBLE
        lldefContainer.visibility = View.GONE
        locationTrackerViewModel.fetchAndSetPreviouseLocations()
    }

    fun onCloseLocationsButtonClicked(view: View) {
        llLocationsContainer.visibility = View.GONE
        lldefContainer.visibility = View.VISIBLE
    }

    override fun onGpsOff() {
        var gpsOff = true
        //val noGPSFragment: NoGPSFragment = NoGPSFragment.newInstance(this, null)
        //NotificationHelper.showFragmentDialog(this, getString(R.string.gps_off),R.layout.fragment_no_g, noGPSFragment)
        // dialogFragment = noGPSFragment
    }

    override fun onLocationPermissionDenied() {
        //TODO: Do some onLocationPermissionDenied
    }

    override fun initMap() {
        fragmentMap = supportFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?
        fragmentMap?.getMapAsync(this)
    }

     override fun mapReady(googleMap: GoogleMap?) {
         var isGpsON = isGPSOn()

        if (!isGpsON) {
            onGpsOff()
            return
        }

         var dfdf= isGpsON
    }

    override fun onRequestListenerSuccess(location: Location?) {
        val userCoordinates = LatLng(location!!.latitude, location!!.longitude)
        plotUserMarker(
            userCoordinates,
            "You",
            "Your location description"
        )

        goToLocationZoomNoAnimation(userCoordinates, USER_ZOOM)
    }

    fun plotLocationMarkers(locations: List<UserLocation>) {
        googleMap?.clear()
        var indx = 0
        for (airport in locations) {
            val tag = "L$indx"
            val name = airport.name
            val description = airport.description
            val airportCoordinates = airport.coordinates
            plotAirportMarker(airportCoordinates, name, description, tag) //"${airport.coordinates.latitude} | ${airport.coordinates.longitude}"
            indx++
        }
        listenForMarkerClicks()
        goToLocationZoomAnimated(airportMarkers[0].position, LOCATION_ZOOM)
    }

    fun plotAirportMarker(latLng: LatLng?, title: String?,  snippet: String?, tag: String?) {
        val airportMarker = getMarker(latLng, title, snippet, tag)
        //val markerIcon = getBitmapDescriptor(R.drawable.ic_pin)
        //airportMarker.setIcon(markerIcon)
        airportMarkers.add(airportMarker)
    }

    protected fun listenForMarkerClicks() {
        googleMap!!.setOnMarkerClickListener(OnMarkerClickListener { marker ->
            val selectedMarkerTag = marker.tag.toString()
            if (selectedMarkerTag == USER_MARKER_TAG) return@OnMarkerClickListener false
            try {
                var markerIndex = 0
                for (airportMarker in airportMarkers) {
                    val currentMarkerTag = airportMarker.tag.toString()
                    if (selectedMarkerTag == currentMarkerTag) {
                        showLocationsRecyclerView()
                        //Go to position (markerIndex)
                        break
                    }
                    ++markerIndex
                }
            } catch (e: Exception) {
                Log.e("MARKER_CLICK_ERROR", "Marker click error: $e")
            }
            false
        })
    }

    private fun onShowLoading(isBusy: Boolean) {
        showLoadingDialog(locationTrackerViewModel.busyMessage, this)
    }


    override fun onLocationChanged(location: Location?) {
       // val currentCoordinates = LatLng(location!!.latitude, location.longitude)
      //  if (getPresenter().isMoved25Meters(currentCoordinates, userMarker!!.position)) {
      //      moveUserMarker(currentCoordinates)
      //  }
    }

}