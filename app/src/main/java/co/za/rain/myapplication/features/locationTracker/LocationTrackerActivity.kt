package co.za.rain.myapplication.features.locationTracker

import android.os.Bundle
import co.za.rain.myapplication.R
import co.za.rain.myapplication.extensions.DEFAULT_STATUS_BAR_ALPHA
import co.za.rain.myapplication.extensions.setTranslucentStatusBar
import co.za.rain.myapplication.features.base.activity.BaseParentActivity

class LocationTrackerActivity : BaseParentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_tracker)

        setTranslucentStatusBar(DEFAULT_STATUS_BAR_ALPHA)
    }
}