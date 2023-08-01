package com.example.weatherservice2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.utils.Constants;
import com.example.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WeatherDataServiceAPI {
    private static final String QUERY_FOR_CITY_COORDINATES = "https://api.api-ninjas.com/v1/city?name=";
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private final Context context;

    public WeatherDataServiceAPI(Context context) {
        this.context = context;
    }

    //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&daily=weathercode,temperature_2m_max,temperature_2m_min,uv_index_max,rain_sum&timezone=Europe%2FBerlin
    private static String getCityWeatherURL(double latitude, double longitude) {
        String queryParams = String.format(
                Locale.US,
                "?latitude=%.2f&longitude=%.2f&daily=weathercode,temperature_2m_max,temperature_2m_min,uv_index_max,rain_sum&timezone=Europe%%2FBerlin",
                latitude,
                longitude
        );
        return BASE_URL + queryParams;
    }

    public CompletableFuture<Coordinate> getCityCoordinateAsync(String cityName) {
        String url = QUERY_FOR_CITY_COORDINATES + cityName;
        CompletableFuture<Coordinate> completableFuture = new CompletableFuture<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        var jso = response.getJSONObject(0);
                        completableFuture.complete(new Coordinate(jso.getDouble("longitude"), jso.getDouble("latitude")));
                    } catch (JSONException e) {
                        completableFuture.completeExceptionally(e);
                    }
                },
                error -> completableFuture.completeExceptionally(new Exception("Something wrong"))
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", Constants.apiKey);
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return completableFuture;
    }

    public CompletableFuture<ArrayList<WeatherModel>> getCityWeatherForWeek(CityData cityData) {
        String url = getCityWeatherURL(cityData.getLatitude(), cityData.getLongitude());
        CompletableFuture<ArrayList<WeatherModel>> completableFuture = new CompletableFuture<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<WeatherModel> weather = extractDataFromJsonObjectToWeatherModel(cityData ,response);
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
                weatherModels.add(new WeatherModel(cityData, weatherCode, tempMax, tempMin, uv_index_max, rain_sum));
            }
        } catch (JSONException e) {
            Util.log(e.getMessage());
            return null;
        }
        return weatherModels;
    }

}
