package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.rain.myapplication.database.WSLDatabase
import java.lang.IllegalArgumentException

class SignalStrengthViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignalStrengthViewModel::class.java)){
            var database = WSLDatabase.getInstance(application)
            val repository = SignalStrengthRepository(database)
            return SignalStrengthViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}