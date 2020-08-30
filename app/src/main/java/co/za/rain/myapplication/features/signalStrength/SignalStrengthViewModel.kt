package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.ConnectionType
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import co.za.rain.myapplication.features.locationTracker.LocationTrackerRepository

class SignalStrengthViewModel(application: Application, private val signalStrengthRepository: SignalStrengthRepository) : BaseVieModel(application) {

    private val minRSRP: Int = 0
    private val maxRSRP: Int = 170

    private val _wifiPowerPercentage: MutableLiveData<Int> = MutableLiveData()
    val wifiPowerPercentage: MutableLiveData<Int>
        get() = _wifiPowerPercentage

    private val _mobilePowerPercentage: MutableLiveData<Int> = MutableLiveData()
    val mobilePowerPercentage: MutableLiveData<Int>
        get() = _mobilePowerPercentage

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

    fun setData(rsrp: Int, rssi: Int, connectionType: ConnectionType){
        when(connectionType) {
            ConnectionType.Wifi ->{
                _wifiRSRP.value = "$rsrp mb"
                _wifiRSSI.value = "$rssi mb"
                _mobileRSSI.value = "0"
                _wifiPowerPercentage.value = (rsrp + rssi) * 100 / maxRSRP
                _mobilePowerPercentage.value = 0
            }
            ConnectionType.Mobile ->{
                _wifiRSRP.value = "0"
                _wifiRSSI.value = "0"
                _mobileRSRP.value = "$rsrp mb"
                _mobileRSSI.value = "$rssi mb"

                _wifiPowerPercentage.value = 0
                _mobilePowerPercentage.value = (rsrp + rssi) * 100 / maxRSRP
            }
        }



        val total = rsrp + rssi
        _total.value = "$total mb"
    }
}