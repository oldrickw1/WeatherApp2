package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.utils.Constants;
import com.example.utils.Util;

import java.io.File;
import java.util.ArrayList;

import kotlin.jvm.internal.Ref;

public class SelectACity extends AppCompatActivity {
    AutoCompleteTextView selectCityACTV;
    TextView displayLoadingTV;
    ProgressBar circularProgressBar;

    CitiesDAO citiesDAO;
    myDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_city);

        databaseHelper = new myDatabaseHelper(this);
        databaseHelper.copyDatabaseFromAssets(this);
        citiesDAO = new CitiesDAO(databaseHelper.getReadableDatabase());

        ArrayList<CityData> cities;
        try {

            cities = citiesDAO.getAllCities();
        } catch (Exception e) {
            Util.log(e.getMessage());
            cities = new ArrayList<>();
        }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        selectCityACTV.setText("");
    }

    private void startIntentForSelectedCity(CityData cityData) {
        Intent intent = new Intent(this, WeatherInfoDisplay.class);
        intent.putExtra("cityName", cityData.getCityName());
        intent.putExtra("country", cityData.getCountry());
        intent.putExtra("latitude", cityData.getLatitude());
        intent.putExtra("longitude" , cityData.getLongitude());
        startActivity(intent);
    }
}