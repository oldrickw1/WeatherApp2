package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class SelectACity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_a_city);

        ArrayList<CityData> cities = getCityData();
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

    private ArrayList<CityData> getCityData() {
        // dummy data
        return new ArrayList<>(List.of(
                new CityData("Amsterdam", "The Netherlands"),
                new CityData("Brussels", "Belgium"),
                new CityData("London", "The United Kingdom"),
                new CityData("Madrid", "Spain"),
                new CityData("Berlin", "Germany"),
                new CityData("Paris", "France"),
                new CityData("Rome", "Italy"),
                new CityData("Vienna", "Austria"),
                new CityData("Stockholm", "Sweden"),
                new CityData("Copenhagen", "Denmark"),
                new CityData("Lisbon", "Portugal"),
                new CityData("Prague", "Czech Republic"),
                new CityData("Budapest", "Hungary"),
                new CityData("Warsaw", "Poland"),
                new CityData("Athens", "Greece"),
                new CityData("Dublin", "Ireland"),
                new CityData("Oslo", "Norway"),
                new CityData("Helsinki", "Finland"),
                new CityData("Bratislava", "Slovakia"),
                new CityData("Bucharest", "Romania"),
                new CityData("Sofia", "Bulgaria"),
                new CityData("Zagreb", "Croatia"),
                new CityData("Ljubljana", "Slovenia"),
                new CityData("Belgrade", "Serbia"),
                new CityData("Skopje", "North Macedonia"),
                new CityData("Tirana", "Albania"),
                new CityData("Vilnius", "Lithuania"),
                new CityData("Riga", "Latvia"),
                new CityData("Tallinn", "Estonia"),
                new CityData("Valletta", "Malta"),
                new CityData("Reykjavik", "Iceland"),
                new CityData("Nicosia", "Cyprus")
        )
        );
    };
}