package co.za.rain.myapplication.features.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.rain.myapplication.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }
}