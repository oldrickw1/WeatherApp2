package com.example.weatherservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherInfoDisplay extends AppCompatActivity {
    TextView cityNameDisplayTV;
    TextView cityWeatherDisplayTV;
    TextView dataTV;
    RecyclerView recyclerView;

    WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info_display);


        cityNameDisplayTV = findViewById(R.id.cityNameTV);
        cityWeatherDisplayTV = findViewById(R.id.countryNameTV);
        dataTV = findViewById(R.id.dateTV);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);




        getWeather(getCityData());

    }

    @NonNull
    private CityData getCityData() {
        String cityName = getIntent().getExtras().getString("cityName");
        String country = getIntent().getExtras().getString("country");
        double latitude = getIntent().getExtras().getDouble("latitude",10);
        double longitude = getIntent().getExtras().getDouble("longitude",10);
        return new CityData(cityName,country,latitude,longitude);
    }

    private void getWeather(CityData city) {
        weatherAPI.getCityWeatherForWeek(city).thenAccept(results -> {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(results);
            recyclerView.setAdapter(adapter);
        });
    }



}
