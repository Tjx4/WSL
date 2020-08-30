package co.za.rain.myapplication.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.rain.myapplication.extensions.FADE_IN_ACTIVITY
import co.za.rain.myapplication.extensions.navigateToActivity
import co.za.rain.myapplication.features.locationTracker.LocationTrackerActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToActivity(LocationTrackerActivity::class.java, FADE_IN_ACTIVITY)
        finish()
    }
}