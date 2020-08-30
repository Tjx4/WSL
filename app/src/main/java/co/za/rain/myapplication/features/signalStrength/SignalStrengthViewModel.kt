package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.features.locationTracker.LocationTrackerRepository

class SignalStrengthViewModel(application: Application, private val signalStrengthRepository: SignalStrengthRepository) : BaseVieModel(application) {
}