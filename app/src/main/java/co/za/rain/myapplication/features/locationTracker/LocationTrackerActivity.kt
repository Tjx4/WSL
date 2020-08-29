package co.za.rain.myapplication.features.locationTracker

import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import co.za.rain.myapplication.R
import co.za.rain.myapplication.adapters.LocationsAdapter
import co.za.rain.myapplication.databinding.ActivityLocationTrackerBinding
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.blinkView
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseMapActivity
import co.za.rain.myapplication.models.UserLocation
import com.google.android.gms.maps.GoogleMap
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
        locationTrackerViewModel.locations.observe(this, Observer { onLocationsUpdated(it) })
    }

    fun onLocationsUpdated(locations: List<UserLocation>) {
        var  locationsAdapter = LocationsAdapter(this, locations)
        locationsAdapter.setLocationClickListener(this)

        val categoryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryLayoutManager.initialPrefetchItemCount = locations.size

        rvLocations?.layoutManager = categoryLayoutManager
        rvLocations?.adapter = locationsAdapter

        val snapHelper: SnapHelper = object : PagerSnapHelper() {
            override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
                var indx = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                var maxLocations = locationTrackerViewModel.locations.value?.size ?: 0
                var fntIndx = if(indx >= maxLocations) { maxLocations } else { indx + 1}
                locationTrackerViewModel.setLocationIndx(fntIndx)
                return indx
            }
        }

        snapHelper.attachToRecyclerView(rvLocations)
    }

    override fun onServiceCategoryClick(view: View, position: Int) {

    }

    fun onSaveLocationButtonClicked(view: View) {
         Toast.makeText(this, "onSaveLocationButtonClicked", Toast.LENGTH_LONG).show()
    }

    fun onShowLocationsButtonClicked(view: View) {
        var v = view.parent as View

        v.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            llLocationsContainer.visibility = View.VISIBLE
            lldefContainer.visibility = View.GONE
        }, {})
    }

    fun onCloseLocationsButtonClicked(view: View) {
        view.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
            llLocationsContainer.visibility = View.GONE
            lldefContainer.visibility = View.VISIBLE
        }, {})
    }

    override fun onGpsOff() {
        var gpsOff = true
        //val noGPSFragment: NoGPSFragment = NoGPSFragment.newInstance(this, null)
        //NotificationHelper.showFragmentDialog(this, getString(R.string.gps_off),R.layout.fragment_no_g, noGPSFragment)
        // dialogFragment = noGPSFragment
    }

    override fun onLocationPermissionDenied() {
        // Do some
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
            "Location description"
        )

        goToLocationZoomNoAnimation(userCoordinates, USER_ZOOM)
    }

    override fun onLocationChanged(location: Location?) {
       // val currentCoordinates = LatLng(location!!.latitude, location.longitude)
      //  if (getPresenter().isMoved25Meters(currentCoordinates, userMarker!!.position)) {
      //      moveUserMarker(currentCoordinates)
      //  }
    }

}