package co.za.rain.myapplication.features.signalStrength

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.enums.ConnectionType
import co.za.rain.myapplication.R
import co.za.rain.myapplication.constants.CON_TYPE
import co.za.rain.myapplication.constants.GET_SIGNAL_STRENGTH
import co.za.rain.myapplication.constants.RSRP
import co.za.rain.myapplication.constants.RSSI
import co.za.rain.myapplication.databinding.ActivitySignalStrengthBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import co.za.rain.myapplication.services.SignalMonitorService
import kotlinx.android.synthetic.main.activity_signal_strength.*

class SignalStrengthActivity : BaseChildActivity() {
    private lateinit var binding: ActivitySignalStrengthBinding
    protected lateinit var signalStrengthViewModel: SignalStrengthViewModel
    private lateinit var serviceIntent: Intent
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var application = requireNotNull(this).application
        var viewModelFactory = SignalStrengthViewModelFactory(application)

        signalStrengthViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            SignalStrengthViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signal_strength)
        binding.signalStrengthViewModel = signalStrengthViewModel
        binding.lifecycleOwner = this

        addObservers()

        supportActionBar?.title = getString(R.string.signal_monitor)

        serviceIntent = Intent(this, SignalMonitorService::class.java)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    private fun addObservers() {
        signalStrengthViewModel.wifiPowerPercentage.observe(this, Observer { onWifiPowerPercentageSet(it) })
        signalStrengthViewModel.mobilePowerPercentage.observe(this, Observer { onMobilePowerPercentageSet(it) })

    }

    fun onWifiPowerPercentageSet(amount: Int) {
        gvWifi.moveToValue(amount.toFloat())
    }

    fun onMobilePowerPercentageSet(amount: Int) {
        gvMobile.moveToValue(amount.toFloat())
    }

    override fun onStart() {
        super.onStart()
        // startService(serviceIntent)
       // registerReceiver(receiver, IntentFilter("GET_SIGNAL_STRENGTH"))
    }

    override fun onStop() {
        super.onStop()
        stopService(serviceIntent)
        unregisterReceiver(receiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in permissions.indices) {
            val permission = permissions[i]
            val grantResult = grantResults[i]

            if (permission == Manifest.permission.ACCESS_COARSE_LOCATION) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    startService(serviceIntent)
                    initBroadcastReceiver()
                } else {
                    onLocationPermissionDenied()
                }
            }
        }
    }

    private fun initBroadcastReceiver() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == GET_SIGNAL_STRENGTH) {
                    val rsrp = intent.extras?.getInt(RSRP) ?: 0
                    val rssi = intent.extras?.getInt(RSSI) ?: 0
                    val con = intent.extras?.getInt(CON_TYPE) ?: 0
                    val conType = ConnectionType.values()[con]

                    signalStrengthViewModel.setData(rsrp, rssi, conType)
                }
            }
        }
        registerReceiver(receiver, IntentFilter(GET_SIGNAL_STRENGTH))
    }


    fun onLocationPermissionDenied(){
        Toast.makeText(this, "onLocationPermissionDenied", Toast.LENGTH_LONG).show()
    }

}