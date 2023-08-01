package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utils.Constants;
import com.example.utils.Util;

import java.util.ArrayList;

public class SelectACity extends AppCompatActivity {
    AutoCompleteTextView selectCityACTV;
    TextView displayLoadingTV;
    ProgressBar circularProgressBar;
    WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_city);

        ArrayList<CityData> cities = Constants.getCities();
        selectCityACTV = findViewById(R.id.autoCompleteTextView);
        displayLoadingTV = findViewById(R.id.displayLoading);
        circularProgressBar =  findViewById(R.id.circularProgressBar);


        AutoCompleteCityAdapter adapter = new AutoCompleteCityAdapter(this, cities);
        selectCityACTV.setAdapter(adapter);

        selectCityACTV.setOnItemClickListener((parent, view, position, id) -> {
            CityData cityData = (CityData) parent.getItemAtPosition(position);
            startIntentForSelectedCity(cityData);
        });

    }



    private void startIntentForSelectedCity(CityData cityData) {
        Intent intent = new Intent(this, WeatherInfoDisplay.class);
        intent.putExtra("cityName", cityData.getCityName());
        intent.putExtra("country", cityData.getCountry());
        intent.putExtra("latitude", cityData.getLatitude());
        intent.putExtra("longitude" , cityData.getLongitude());
        startActivity(intent);
    }

    private void displayLoadingMessage(String cityName) {
        selectCityACTV.setVisibility(View.INVISIBLE);
        displayLoadingTV.setText("Getting the forecast for " + cityName + "!");
        circularProgressBar.setVisibility(View.VISIBLE);
        displayLoadingTV.setVisibility(View.VISIBLE);


    }
}