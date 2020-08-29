package co.za.rain.myapplication.features.locationTracker

import android.location.Location
import android.os.Bundle
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseMapActivity
import co.za.rain.myapplication.features.base.activity.BaseParentActivity

class LocationTrackerActivity : BaseMapActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_tracker)

        setTranslucentStatusBar(DEFAULT_STATUS_BAR_ALPHA)
    }

    override fun onGpsOff() {
        TODO("Not yet implemented")
    }

    override fun onLocationPermissionDenied() {
        TODO("Not yet implemented")
    }

    override fun initMap() {
        TODO("Not yet implemented")
    }

    override fun onRequestListenerSuccess(location: Location?) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location?) {
        TODO("Not yet implemented")
    }
}