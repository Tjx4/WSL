package co.za.rain.myapplication.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellSignalStrengthLte
import android.telephony.TelephonyManager
import co.za.rain.myapplication.constants.CON_TYPE
import co.za.rain.myapplication.constants.GET_SIGNAL_STRENGTH
import co.za.rain.myapplication.constants.RSRP
import co.za.rain.myapplication.constants.RSSI
import co.za.rain.myapplication.helpers.Connectivity

class SignalMonitorService : Service() {
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sendLevel = Intent()
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                val signalInfo = checkPhoneSignal()
                var rsrp = signalInfo[RSRP]
                var rssi = signalInfo[RSSI]
                var connectionType = Connectivity.getConnectionType(context)

                sendLevel?.action = GET_SIGNAL_STRENGTH
                sendLevel?.putExtra(RSRP, rsrp)
                sendLevel?.putExtra(RSSI, rssi)
                sendLevel?.putExtra(CON_TYPE, connectionType.id)
                sendBroadcast(sendLevel);

                mainHandler.postDelayed(this, 10000)
            }
        })
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    fun checkPhoneSignal(): Map<String, Int>{
        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val cellInfoList = tm.allCellInfo
        var rsrp = 0
        var rssi = 0

        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoLte) {
                var cellSignalStrengthLte = cellInfo.cellSignalStrength as CellSignalStrengthLte

                rsrp = cellSignalStrengthLte.rsrp
                rssi = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                     cellSignalStrengthLte.rssi
                }
                else{
                    0
                }
            }
            else if (cellInfo is CellInfoGsm) {
                var cellSignalStrength = cellInfo.cellSignalStrength
                rsrp = cellSignalStrength.dbm
                rssi = cellSignalStrength.asuLevel
            }
        }

        return mapOf(
            RSRP to rsrp,
            RSSI to rssi
        )
    }
}
