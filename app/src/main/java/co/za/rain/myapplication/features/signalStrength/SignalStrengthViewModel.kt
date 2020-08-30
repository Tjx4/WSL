package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel

class SignalStrengthViewModel(application: Application, private val signalStrengthRepository: SignalStrengthRepository) : BaseVieModel(application) {

    private val _wifiRSRP: MutableLiveData<String> = MutableLiveData()
    val wifiRSRP: MutableLiveData<String>
        get() = _wifiRSRP

    private val _wifiRSSI: MutableLiveData<String> = MutableLiveData()
    val wifiRSSI: MutableLiveData<String>
        get() = _wifiRSSI

    init {

    }
}