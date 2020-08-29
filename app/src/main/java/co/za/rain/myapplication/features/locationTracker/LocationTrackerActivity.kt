package co.za.rain.myapplication.features.locationTracker

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseMapActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class LocationTrackerActivity : BaseMapActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_tracker)
        setTranslucentStatusBar(DEFAULT_STATUS_BAR_ALPHA)

        // Todo: fix logic
        if(isGooglePlayServicesAvailable()){
            checkLocationPermissionAndContinue()
        }
        else{
            Toast.makeText(this, "Google play services unavailable", Toast.LENGTH_LONG).show()
        }
    }

    fun onSaveLocationButtonClicked(view: View) {
         Toast.makeText(this, "onSaveLocationButtonClicked", Toast.LENGTH_LONG).show()
    }

    fun onShowLocationButtonClicked(view: View) {
         Toast.makeText(this, "onShowLocationButtonClicked", Toast.LENGTH_LONG).show()
    }

    fun onCloseLocationsButtonClicked(view: View) {
         Toast.makeText(this, "onCloseLocationsButtonClicked", Toast.LENGTH_LONG).show()
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