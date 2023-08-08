package com.example.weatherservice2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CitiesDAO implements IcitiesDAO {
    private final SQLiteDatabase db;

    public CitiesDAO(SQLiteDatabase database) {
        this.db = database;
    }

    @Override
    public ArrayList<CityData> getCitiesStartingWith(String characters) {
        return null;
    }

    @Override
    public ArrayList<CityData> getAllCities() {
        ArrayList<CityData> cities = new ArrayList<>();
        Cursor cursor =  db.query("cities", new String[]{"city","country","lat","lng"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String city = cursor.getString(0);
            String country = cursor.getString(1);
            double lat = cursor.getDouble(2);
            double lng = cursor.getDouble(3);
            cities.add(new CityData(city,country,lat,lng));
        }
        cursor.close();
        db.close();
        return cities;
    }

}
