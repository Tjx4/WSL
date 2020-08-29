package co.za.rain.myapplication.features.locationTracker

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseMapActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_location_tracker.*

class LocationTrackerActivity : BaseMapActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_tracker)

        setTranslucentStatusBar(DEFAULT_STATUS_BAR_ALPHA)
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
        //mapFragment = FragmentManager.findFragmentById(R.id.mapFragment)
        mapFragment?.getMapAsync(this)
       // mvMyMap?.getMapAsync(this)

    }

     override fun mapReady(googleMap: GoogleMap?) {
        if (!isGPSOn()) {
            onGpsOff()
            return
        }
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