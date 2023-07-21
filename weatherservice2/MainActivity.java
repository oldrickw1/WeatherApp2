package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button getCityCoordinates, getWeatherFromCoordinates, getWeatherFromCityName;
    private EditText cityInputET, latitudeET, longitudeET;
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
//        HashMap<String, Double> coordinates = weatherService.getCityCoordinates("London");
//        coordinates.entrySet().forEach(e-> {
//            Util.log(e.toString());
//        });



        getCityCoordinates.setOnClickListener(v -> {
            String cityName = cityInputET.getText().toString().trim();
            weatherService.getCityCoordinate(cityName, new VolleyResponseListener() {
                @Override
                public void onError(String s) {
                    Util.log(s);
                }

                @Override
                public void onResponse(Object o) {
                    Coordinate coordinate = (Coordinate) o;
                    latitudeET.setText(String.valueOf(coordinate.getLatitude()));
                    longitudeET.setText(String.valueOf(coordinate.getLongitude()));
                }
            });
        });

        getWeatherFromCoordinates.setOnClickListener(v -> {
            Util.toast(this, "Clicked useCityLocationBtn");
        });

        getWeatherFromCityName.setOnClickListener(v -> {
            Util.toast(this, "Clicked useCityNameBtn");
        });


    }



    private void setUp() {
        getCityCoordinates = findViewById(R.id.get_city_geolocation_btn);
        getWeatherFromCoordinates = findViewById(R.id.use_location_btn);
        getWeatherFromCityName = findViewById(R.id.use_city_name_btn);
        cityInputET = findViewById(R.id.city_input_et);
        latitudeET = findViewById(R.id.latitude_et);
        longitudeET = findViewById(R.id.longitude_et);
        weatherResultRV = findViewById(R.id.weatherRV);

        longitude = 0;
        latitude = 0;

    }
}