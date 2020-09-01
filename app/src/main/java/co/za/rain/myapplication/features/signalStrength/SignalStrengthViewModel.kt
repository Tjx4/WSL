package co.za.rain.myapplication.features.signalStrength

import android.app.Application
import android.telephony.SignalStrength
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.database.tables.signalStats.SignalStatsTable
import co.za.rain.myapplication.enums.ConnectionType
import co.za.rain.myapplication.features.base.viewmodel.BaseVieModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SignalStrengthViewModel(application: Application, var signalStrengthRepository: SignalStrengthRepository) : BaseVieModel(application) {

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

                _wifiPowerPercentage.value = getStrengthPercentage(rsrp, rssi)
                _mobilePowerPercentage.value = 0
            }
            ConnectionType.Mobile ->{
                _wifiRSRP.value = "0"
                _wifiRSSI.value = "0"
                _mobileRSRP.value = "$rsrp mb"
                _mobileRSSI.value = "$rssi mb"

                _wifiPowerPercentage.value = 0
                _mobilePowerPercentage.value = getStrengthPercentage(rsrp, rssi)
            }
        }

        val total = rsrp + rssi
        val totalDisplay = "$total mb"
        _total.value = totalDisplay
        saveConnectionStats(totalDisplay)
    }

    fun getStrengthPercentage(rsrp: Int, rssi: Int): Int{
        val power = (rsrp + rssi)
        var strength = power * 100 / maxRSRP
        return strength
    }

    fun saveConnectionStats(strength: String){
        val stats = SignalStatsTable()
        stats.strength = strength
        stats.dateTime = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
        ioScope.launch {
            signalStrengthRepository.addStatsToDb(stats)
        }
    }
}