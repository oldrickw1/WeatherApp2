package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.example.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SelectACity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_city);

        ArrayList<CityData> cities = Constants.getCities();
        AutoCompleteTextView selectCityACTV = findViewById(R.id.autoCompleteTextView);

        AutoCompleteCityAdapter adapter = new AutoCompleteCityAdapter(this, cities);
        selectCityACTV.setAdapter(adapter);

        selectCityACTV.setOnItemClickListener((parent, view, position, id) -> {
            CityData cityData = (CityData) parent.getItemAtPosition(position);
            startIntentForSelectedCity(cityData);
        });


    }

    private void startIntentForSelectedCity(CityData cityData) {
        Intent intent = new Intent(this, WeatherInfoDisplay.class);
        intent.putExtra("CityName", cityData.getCityName());
        intent.putExtra("CityCountry", cityData.getCountry());
        startActivity(intent);
    }
}