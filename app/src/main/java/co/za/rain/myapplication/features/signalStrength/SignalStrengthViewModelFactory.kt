package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignalStrengthViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignalStrengthViewModel::class.java)){
            val repository = SignalStrengthRepository()
            return SignalStrengthViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}