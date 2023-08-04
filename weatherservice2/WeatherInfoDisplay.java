package com.example.weatherservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.utils.Util;

import java.util.ArrayList;

public class WeatherInfoDisplay extends AppCompatActivity {
    private TextView cityNameDisplayTV;
    private TextView cityWeatherDisplayTV;
    private TextView dataTV;
    private RecyclerView recyclerView;
    private ImageView imageView2;

    private WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);
    private TextView countryNameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info_display);


        cityNameDisplayTV = findViewById(R.id.cityNameTV);
        countryNameTV = findViewById(R.id.countryNameTV);
        cityWeatherDisplayTV = findViewById(R.id.countryNameTV);
        dataTV = findViewById(R.id.dateTV);
        recyclerView = findViewById(R.id.recyclerView);
        imageView2 = findViewById(R.id.imageView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getWeather(getCityData());

    }

    private CityData getCityData() {
        Bundle extras = getIntent().getExtras();
        String cityName = extras.getString("cityName");
        String country = extras.getString("country");
        double latitude = extras.getDouble("latitude",10);
        double longitude = extras.getDouble("longitude",10);
        return new CityData(cityName,country,latitude,longitude);
    }

    private void getWeather(CityData city) {
        cityNameDisplayTV.setText(city.getCityName());
        countryNameTV.setText(city.getCountry());
        weatherAPI.getCityWeatherForWeek(city).thenAccept(results -> {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(results);
            recyclerView.setAdapter(adapter);
            Util.log("WEATHERCODE: " + results.get(0).getWeatherCode());
            Glide.with(this)
                    .load("http://openweathermap.org/img/wn/01d@2x.png")
                    .into(imageView2);
        });
    }



}
