package co.za.rain.myapplication.features.signalStrength

import android.os.Bundle
import android.telephony.CellSignalStrength
import android.telephony.CellSignalStrengthLte
import android.telephony.TelephonyManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.R
import co.za.rain.myapplication.databinding.ActivitySignalStrengthBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import kotlinx.android.synthetic.main.activity_signal_strength.*


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

    }
}