package com.example.weatherservice2;

import android.database.Cursor;

import java.util.ArrayList;

public interface IcitiesDAO {
    ArrayList<CityData> getCitiesStartingWith(String characters);
    ArrayList<CityData>  getAllCities();
}
