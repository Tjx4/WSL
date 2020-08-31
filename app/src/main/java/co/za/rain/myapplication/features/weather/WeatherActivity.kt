package co.za.rain.myapplication.features.weather

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.za.rain.myapplication.R
import co.za.rain.myapplication.constants.PAYLOAD_KEY
import co.za.rain.myapplication.constants.USER_LOCATION
import co.za.rain.myapplication.databinding.ActivityWeatherBinding
import co.za.rain.myapplication.features.base.activity.BaseChildActivity
import co.za.rain.myapplication.helpers.hideCurrentLoadingDialog
import co.za.rain.myapplication.helpers.showErrorAlert
import co.za.rain.myapplication.helpers.showLoadingDialog
import co.za.rain.myapplication.models.UserLocation
import co.za.rain.myapplication.models.WeatherModel

class WeatherActivity : BaseChildActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherViewModel: WeatherViewModel

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
        weatherViewModel.checkAndSetLocation(userLocation)
    }

    private fun addObservers() {
        weatherViewModel.showLoading.observe(this, Observer { onShowLoading(it) })
        weatherViewModel.currentLocation.observe(this, Observer { onCurrentLocationSet(it) })
        weatherViewModel.weather.observe(this, Observer { onWeatherSet(it) })
        weatherViewModel.isNoLocation.observe(this, Observer { onNoLocation(it) })
    }

    private fun onShowLoading(isBusy: Boolean) {
        showLoadingDialog(weatherViewModel.busyMessage.value ?: "", this)
    }

    private fun onCurrentLocationSet(location: UserLocation) {
        weatherViewModel.getLocationWeather()
    }

    private fun onWeatherSet(weather: WeatherModel) {
        hideCurrentLoadingDialog(this)
    }

    private fun onNoLocation(isBusy: Boolean) {
        showErrorAlert(this, getString(R.string.error), getString(R.string.no_location_message), getString(R.string.ok)) {
            finish()
        }
    }

    fun onBackButtonClicked(view: View) = onBackPressed()
}