package co.za.rain.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.rain.myapplication.extensions.FADE_IN_ACTIVITY
import co.za.rain.myapplication.extensions.navigateToActivity
import co.za.rain.myapplication.features.locationTracker.LocationTrackerActivity
import co.za.rain.myapplication.features.signalStrength.SignalStrengthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToActivity(LocationTrackerActivity::class.java, FADE_IN_ACTIVITY)
    }
}