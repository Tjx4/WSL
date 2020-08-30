package co.za.rain.myapplication.features.locationTracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.rain.myapplication.database.WSLDatabase
import java.lang.IllegalArgumentException

class LocationTrackerViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocationTrackerViewModel::class.java)){
            var database = WSLDatabase.getInstance(application)
            val repository = LocationTrackerRepository(application, database)
            return LocationTrackerViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}