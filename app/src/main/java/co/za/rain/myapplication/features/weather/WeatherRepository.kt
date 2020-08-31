package co.za.rain.myapplication.features.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.rain.myapplication.constants.HOST
import co.za.rain.myapplication.helpers.RetrofitHelper
import co.za.rain.myapplication.models.WeatherModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(private var retrofitHelper: RetrofitHelper) {
     fun getWeather(apiKey: String, latLng: LatLng) : LiveData<WeatherModel> {
        val liveData = MutableLiveData<WeatherModel>()
        retrofitHelper.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)?.enqueue(object: Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                liveData.value = WeatherModel("", null)
            }

        })

        return liveData
    }

    suspend fun getWeatherAsync(apiKey: String, latLng: LatLng) : WeatherModel? {
        try {
            return  retrofitHelper.getMyLocationWeatherAsync(apiKey, latLng?.latitude, latLng?.longitude)
        }
        catch (ex: Exception){
            return WeatherModel("", null)
        }
    }
}