package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.utils.Util;

public class WeatherInfoDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info_display);

        TextView cityNameDisplayTV = findViewById(R.id.cityNameDisplay);
        TextView cityWeatherDisplayTV = findViewById(R.id.cityWeather);

        String cityName = getIntent().getStringExtra("CityName");
        String countryName = getIntent().getStringExtra("CityCountry");

        cityNameDisplayTV.setText(cityName + ", " + countryName);

        WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);
        weatherAPI.getCityCoordinateAsync(cityName)
                .thenCompose(coordinate -> weatherAPI.getCityWeather(coordinate))
                .thenAccept(weather -> {
                    runOnUiThread(() -> {
                        cityWeatherDisplayTV.setText(weather.toString());
                        Util.log("Weather: " + weather);
                    });
                })
                .exceptionally(ex -> {
                    Util.log("Error fetching weather: " + ex.getMessage());
                    return null;
                });
    }
}
