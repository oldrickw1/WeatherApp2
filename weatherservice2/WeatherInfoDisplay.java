package com.example.weatherservice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherInfoDisplay extends AppCompatActivity {
    private TextView cityNameDisplayTV;
    private TextView geoLocationTV;

    private final WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);
    private TextView countryNameTV;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);


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
        weatherAPI.getCityWeatherForWeek(city).thenAccept(results -> {
            Log.i("RESULT", "getWeather: result: " + results.toString());
            setContentView(R.layout.activity_weather_info_display);
            countryNameTV = findViewById(R.id.countryNameTV);
            geoLocationTV = findViewById(R.id.geoLocation);
            cityNameDisplayTV = findViewById(R.id.cityNameTV);
            tabLayout = findViewById(R.id.tabLayout);
            viewPager2 = findViewById(R.id.pager);

            cityNameDisplayTV.setText(city.getCityName());
            countryNameTV.setText(city.getCountry());
            geoLocationTV.setText("(" +   city.getLatitude() + " , " + city.getLongitude() + ")");


            WeatherFragmentStateAdapter weatherFragmentStateAdapter = new WeatherFragmentStateAdapter(this, getSupportFragmentManager(), getLifecycle(), results);
            viewPager2.setAdapter(weatherFragmentStateAdapter);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(results.get(position).getDate().substring(0,2))).attach();
        });
    }
}
