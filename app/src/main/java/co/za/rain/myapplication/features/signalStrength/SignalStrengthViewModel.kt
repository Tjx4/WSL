package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.ConnectionType
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.features.locationTracker.LocationTrackerRepository

class SignalStrengthViewModel(application: Application, private val signalStrengthRepository: SignalStrengthRepository) : BaseVieModel(application) {

    private val _wifiRSRP: MutableLiveData<String> = MutableLiveData()
    val wifiRSRP: MutableLiveData<String>
        get() = _wifiRSRP

    private val _wifiRSSI: MutableLiveData<String> = MutableLiveData()
    val wifiRSSI: MutableLiveData<String>
        get() = _wifiRSSI

    private val _mobileRSRP: MutableLiveData<String> = MutableLiveData()
    val mobileRSRP: MutableLiveData<String>
        get() = _mobileRSRP

    private val _mobileRSSI: MutableLiveData<String> = MutableLiveData()
    val mobileRSSI: MutableLiveData<String>
        get() = _mobileRSSI

    init {

    }

    fun setData(rsrp: String, rssi: String, connectionType: ConnectionType){
        if(connectionType == ConnectionType.Wifi) {
            _wifiRSRP.value = rsrp
            _wifiRSSI.value = rssi
        }
        else {
            _mobileRSRP.value = rsrp
            _mobileRSSI.value = rssi
        }
    }
}