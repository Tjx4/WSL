package co.za.rain.myapplication.features.weather

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.R
import co.za.rain.myapplication.constants.PAYLOAD_KEY
import co.za.rain.myapplication.constants.USER_LOCATION
import co.za.rain.myapplication.databinding.ActivityWeatherBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import co.za.rain.myapplication.helpers.showLoadingDialog
import co.za.rain.myapplication.models.UserLocation

class WeatherActivity : BaseChildActivity() {
    private lateinit var binding: ActivityWeatherBinding
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = WeatherViewModelFactory(application)

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            WeatherViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        binding.weatherViewModel = weatherViewModel
        binding.lifecycleOwner = this

        addObservers()

        var userLocation = intent.getBundleExtra(PAYLOAD_KEY).getParcelable<UserLocation>(USER_LOCATION)
        weatherViewModel.setCurrentLocation(userLocation)
    }

    private fun addObservers() {
        weatherViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        weatherViewModel.currentLocation.observe(this, Observer { onCurrentLocationSet(it) })
    }

    private fun onShowLoading(isBusy: Boolean) {
        showLoadingDialog(weatherViewModel.busyMessage.value ?: "", this)
    }

    private fun onCurrentLocationSet(location: UserLocation) {
        weatherViewModel.getLocationWeather()
    }

}