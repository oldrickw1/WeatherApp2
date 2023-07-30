package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.utils.Util;

public class MainActivity extends AppCompatActivity {
    private Button getCityCoordinates, getWeatherFromCoordinates, getWeatherFromCityName;
    private EditText cityInputET, latitudeET, longitudeET, weatherResultET;
    private RecyclerView weatherResultRV;
    private double latitude;
    private double longitude;
    private WeatherDataService weatherService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.log("TEST");
        setUp();
        weatherService = new WeatherDataService(this);

        getCityCoordinates.setOnClickListener(v -> {
            String cityName = cityInputET.getText().toString().trim();
            weatherService.getCityCoordinateAsync(cityName).thenAccept(coordinate -> {
                latitudeET.setText(Double.toString(coordinate.getLatitude()));
                longitudeET.setText(Double.toString(coordinate.getLongitude()));
            });
        });

        getWeatherFromCoordinates.setOnClickListener(v -> {
            try {
                float latitude = Float.parseFloat(latitudeET.getText().toString());
                float longitude = Float.parseFloat(longitudeET.getText().toString());
                weatherService.getCityWeather(new Coordinate(longitude, latitude)).thenAccept(weatherModel -> weatherResultET.setText(weatherModel.toString()));

            } catch (Exception ignored) {
                Util.toast(this, "Must specify coordinates");
            }

        });

        getWeatherFromCityName.setOnClickListener(v -> {
            String cityName = cityInputET.getText().toString().trim();
            weatherService.getCityCoordinateAsync(cityName).thenCompose(coordinate -> weatherService.getCityWeather(coordinate)).thenAccept(weatherModel -> weatherResultET.setText(weatherModel.toString()));
        });


    }



    private void setUp() {
        getCityCoordinates = findViewById(R.id.get_city_geolocation_btn);
        getWeatherFromCoordinates = findViewById(R.id.use_location_btn);
        getWeatherFromCityName = findViewById(R.id.use_city_name_btn);
        cityInputET = findViewById(R.id.city_input_et);
        latitudeET = findViewById(R.id.latitude_et);
        longitudeET = findViewById(R.id.longitude_et);
        weatherResultET = findViewById(R.id.result);
        weatherResultRV = findViewById(R.id.weatherRV);

        longitude = 0;
        latitude = 0;

    }
}