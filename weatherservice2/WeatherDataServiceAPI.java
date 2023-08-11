package com.example.weatherservice2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class WeatherDataServiceAPI {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private final Context context;

    public WeatherDataServiceAPI(Context context) {
        this.context = context;
    }

    private static String getCityWeatherURL(double latitude, double longitude) {
        String queryParams = String.format(
                Locale.US,
                "?latitude=%.2f&longitude=%.2f&hourly=temperature_2m&daily=weathercode,temperature_2m_max,temperature_2m_min,uv_index_max,rain_sum&timezone=Europe%%2FBerlin",
                latitude,
                longitude
        );
        return BASE_URL + queryParams;
    }

    public CompletableFuture<ArrayList<WeatherModel>> getCityWeatherForWeek(CityData cityData) {
        String url = getCityWeatherURL(cityData.getLatitude(), cityData.getLongitude());
        CompletableFuture<ArrayList<WeatherModel>> completableFuture = new CompletableFuture<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<WeatherModel> weather = extractDataFromJsonObjectToWeatherModel(cityData ,response);
                        addDates(weather);
                        completableFuture.complete(weather);
                    } catch (Exception e) {
                        completableFuture.completeExceptionally(e);
                    }
                },
                error -> {
                    completableFuture.completeExceptionally(new Exception("Something wrong"));
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return completableFuture;
    }

    private void addDates(List<WeatherModel> weatherModelList) {
        for (int i = 0; i < 7; i++) {
            weatherModelList.get(i).setDate(getDate(i));
        }
    }

    private String getDate(int daysOffset) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysOffset);
        String pattern = "EEEE, dd MMMM";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return targetDate.format(formatter);
    }

    private ArrayList<WeatherModel> extractDataFromJsonObjectToWeatherModel(CityData cityData, JSONObject response) {
        ArrayList<WeatherModel> weatherModels = new ArrayList<>();
        try {
            for (int i = 0; i < 7; i++) {
                JSONObject daily = response.getJSONObject("daily");
                int weatherCode = daily.getJSONArray("weathercode").getInt(i);
                float tempMax = (float) daily.getJSONArray("temperature_2m_max").getDouble(i);
                float tempMin = (float) daily.getJSONArray("temperature_2m_min").getDouble(i);
                float uv_index_max = (float) daily.getJSONArray("uv_index_max").getDouble(i);
                float rain_sum = (float) daily.getJSONArray("rain_sum").getDouble(i);
                List<List<WeatherModel.TempDP>> dailyTemperatureDataPoints = getTemps(response);
                weatherModels.add(new WeatherModel(cityData, weatherCode, tempMax, tempMin, uv_index_max, rain_sum, dailyTemperatureDataPoints.get(i)));
            }
        } catch (JSONException e) {
            Util.log(e.getMessage());
            return null;
        }
        return weatherModels;
    }

    private List<List<WeatherModel.TempDP>> getTemps(JSONObject response) {
        List<List<WeatherModel.TempDP>> weekTemps = new ArrayList<>();
        List<WeatherModel.TempDP> dayTemps = new ArrayList<>();
        WeatherModel weatherModel = null;

        try {
            JSONObject hourlyData = response.getJSONObject("hourly");
            JSONArray times = hourlyData.getJSONArray("time");
            JSONArray temperatures = hourlyData.getJSONArray("temperature_2m");
            Log.i("DEBUG", "extractJson: number of timestamps: " +times.length());
            for (int i = 0; i < times.length(); i++) {
                if (i%24 == 0 && i !=0) {
                    dayTemps.add(new WeatherModel.TempDP(24, temperatures.getDouble(i)));
                    weekTemps.add(dayTemps);
                    dayTemps = new ArrayList<>();
                }
                dayTemps.add(new WeatherModel.TempDP(i%24, temperatures.getDouble(i)));
            }
            dayTemps.add(new WeatherModel.TempDP(24, temperatures.getDouble((times.length()-1)) - 0.5f));
            weekTemps.add(dayTemps);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weekTemps;
    }

}
