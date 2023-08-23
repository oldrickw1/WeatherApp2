package com.example.weatherservice2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CitiesDAO {
    private final SQLiteDatabase db;

    private final String TABLE_NAME = "cities";
    private final String COLUMN_CITY = "city";
    private final String COLUMN_LAT = "lat";
    private final String COLUMN_LNG = "lng";
    private final String COLUMN_COUNTRY = "country";

    public CitiesDAO(SQLiteDatabase database) {
        this.db = database;
    }

    public ArrayList<CityData> getAllCities() {
        ArrayList<CityData> cities = new ArrayList<>();
        Cursor cursor =  db.query(TABLE_NAME, new String[]{COLUMN_CITY,COLUMN_COUNTRY,COLUMN_LAT,COLUMN_LNG}, null, null, null, null, null);
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
