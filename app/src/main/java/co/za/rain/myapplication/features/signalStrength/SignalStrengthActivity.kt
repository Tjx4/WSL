package co.za.rain.myapplication.features.signalStrength

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.telephony.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.R
import co.za.rain.myapplication.databinding.ActivitySignalStrengthBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import co.za.rain.myapplication.helpers.Connectivity
import kotlinx.android.synthetic.main.activity_signal_strength.*
import java.lang.reflect.Method


class SignalStrengthActivity : BaseChildActivity() {
    private lateinit var binding: ActivitySignalStrengthBinding
    lateinit var signalStrengthViewModel: SignalStrengthViewModel

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

        //addObservers()

        supportActionBar?.title = "Signal monitor"

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
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
                    checkPhoneSignal()
                } else {
                    onLocationPermissionDenied()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun checkPhoneSignal(){

        var connectionType = ""
        if(Connectivity.isConnectedWifi(this)){
            connectionType = "WIFI"
        }
        else if(Connectivity.isConnectedMobile(this)){
            connectionType = "Mobile"
        }

        var info = Connectivity.getNetworkInfo(this)
        //info.subtype.
        var dfdf = Connectivity.isConnected(this)
        var dd = "$connectionType $info $dfdf"

        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val cellInfoList = tm.allCellInfo

        var ddd = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            tm.signalStrength?.cellSignalStrengths
        } else {

        }

        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoLte) {
                var cellSignalStrengthLte = cellInfo.cellSignalStrength as CellSignalStrengthLte

                tvMobileDataRSRPSpeed.text = "${cellSignalStrengthLte.rsrp} mb"

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    tvMobileDataRSSISpeed.text = "${cellSignalStrengthLte.rssi} mb"
                }
                else{
                    //Todo: get RSSI for < Q
                    tvMobileDataRSSISpeed.text = "0 mb"
                }
            }
            else if (cellInfo is CellInfoGsm) {
                var cellSignalStrength = cellInfo.cellSignalStrength
                tvMobileDataRSRPSpeed.text = "${cellSignalStrength.dbm} mb"
                tvMobileDataRSSISpeed.text = "${cellSignalStrength.asuLevel} mb"
            }
        }
/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

            val cellInfoList: List<CellSignalStrength>
            val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

            cellInfoList = tm.signalStrength!!.cellSignalStrengths

            for (cellInfo in cellInfoList) {
                if (cellInfo is CellSignalStrengthLte) {
                    tvWifiSpeed.text = "${cellInfo.rsrp.toString()} mb"
                    tvMobileDataSpeed.text = "${cellInfo.rssi.toString()} mb"
                }
            }
        }
*/
    }

    fun onLocationPermissionDenied(){
        Toast.makeText(this, "onLocationPermissionDenied", Toast.LENGTH_LONG).show()
    }
}