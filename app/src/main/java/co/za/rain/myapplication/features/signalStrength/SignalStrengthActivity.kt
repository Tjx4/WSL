package co.za.rain.myapplication.features.signalStrength

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.R
import co.za.rain.myapplication.databinding.ActivitySignalStrengthBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import co.za.rain.myapplication.features.signalStrength.SignalStrengthViewModel
import co.za.rain.myapplication.features.signalStrength.SignalStrengthViewModelFactory

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
    }
}