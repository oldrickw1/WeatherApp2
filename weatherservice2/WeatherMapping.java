package com.example.weatherservice2;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class WeatherMapping {
    public static WeatherCodeDetails getWeatherCodeDetails(Context context, int weatherCode) {
        JSONObject jsonObject = getJSONObject(context);
        JSONObject infoForSpecificCodeDuringDay = null;
        try {
            infoForSpecificCodeDuringDay = jsonObject.getJSONObject(String.valueOf(weatherCode)).getJSONObject("day");
            String description = infoForSpecificCodeDuringDay.getString("description");
            String urlToImage = infoForSpecificCodeDuringDay.getString("image");
            Log.i("WEATHERMAPPING", "getWeatherCodeDetails: description: " + description + ", urlToImage: " + urlToImage);
            return new WeatherCodeDetails(description,urlToImage);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static JSONObject getJSONObject(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("weather_mapping.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
