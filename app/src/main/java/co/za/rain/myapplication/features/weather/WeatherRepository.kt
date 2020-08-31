package co.za.rain.myapplication.features.weather

import co.za.rain.myapplication.constants.HOST
import co.za.rain.myapplication.helpers.RetrofitHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository() {
    protected var retrofitHelper: RetrofitHelper

    init {
        val builder = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        retrofitHelper = retrofit.create(RetrofitHelper::class.java)
    }
}