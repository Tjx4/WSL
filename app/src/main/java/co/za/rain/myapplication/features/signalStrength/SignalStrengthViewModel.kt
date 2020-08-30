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

    private val _total: MutableLiveData<String> = MutableLiveData()
    val total: MutableLiveData<String>
        get() = _total

    init {

    }

    fun setData(rsrp: String, rssi: String, connectionType: ConnectionType){
        if(connectionType == ConnectionType.Wifi) {
            _wifiRSRP.value = rsrp
            _wifiRSSI.value = rssi
            _mobileRSRP.value = "0"
            _mobileRSSI.value = "0"
        }
        else {
            _wifiRSRP.value = "0"
            _wifiRSSI.value = "0"
            _mobileRSRP.value = rsrp
            _mobileRSSI.value = rssi
        }

        _total.value = rsrp
    }
}