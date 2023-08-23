package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class SelectACityActivity extends AppCompatActivity {
    AutoCompleteTextView selectCityACTV;
    CitiesDAO citiesDAO;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_city);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.copyDatabaseFromAssets(this);
        citiesDAO = new CitiesDAO(databaseHelper.getReadableDatabase());

        ArrayList<CityData> cities;
        try {
            cities = citiesDAO.getAllCities();
        } catch (Exception e) {
            cities = new ArrayList<>();
        }
        selectCityACTV = findViewById(R.id.autoCompleteTextView);
        AutoCompleteCityAdapter adapter = new AutoCompleteCityAdapter(this, cities);
        selectCityACTV.setAdapter(adapter);
        selectCityACTV.setOnItemClickListener((parent, view, position, id) -> {
            CityData cityData = (CityData) parent.getItemAtPosition(position);
            startIntentForSelectedCity(cityData);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        selectCityACTV.setText("");
    }

    private void startIntentForSelectedCity(CityData cityData) {
        Intent intent = new Intent(this, DisplayWeatherActivity.class);
        intent.putExtra("cityName", cityData.getCityName());
        intent.putExtra("country", cityData.getCountry());
        intent.putExtra("latitude", cityData.getLatitude());
        intent.putExtra("longitude" , cityData.getLongitude());
        startActivity(intent);
    }
}