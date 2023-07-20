package com.example.weatherservice2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherDataService {
    private static final String QUERY_FOR_CITY_COORDINATES = "https://api.api-ninjas.com/v1/city?name=";

    public HashMap<String, Double> getCityCoordinates(String cityName, Context context) {
        String url = QUERY_FOR_CITY_COORDINATES + cityName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONObject jsonObject;
                    double latitude, longitude;
                    try {
                        jsonObject = response.getJSONObject(0);
                        latitude = jsonObject.getDouble("latitude");
                        longitude = jsonObject.getDouble("longitude");
                        Util.toast(context, "Lat: " + latitude + ", Lon: " + longitude);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Util.toast(context, error.toString());
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  headers = new HashMap<>();
                headers.put("X-Api-Key", Constants.apiKey);
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return null;
    }

}
