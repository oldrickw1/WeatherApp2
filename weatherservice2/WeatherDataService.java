package com.example.weatherservice2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class WeatherDataService {
    private static final String QUERY_FOR_CITY_COORDINATES = "https://api.api-ninjas.com/v1/city?name=";
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    private Context context;
    Coordinate coordinate;


    public WeatherDataService(Context context) {
        this.context = context;
    }

    public void getCityCoordinate(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_COORDINATES + cityName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,null,
                response -> {
                    try {
                        JSONObject jso = response.getJSONObject(0);
                        volleyResponseListener.onResponse(new Coordinate(jso.getDouble("longitude"),jso.getDouble("latitude")));
                    } catch (JSONException e) {
                        volleyResponseListener.onError(e.toString());
                    }
                },
                error -> volleyResponseListener.onError("Something wrong")
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  headers = new HashMap<>();
                headers.put("X-Api-Key", Constants.apiKey);
                return headers;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getCityWeather(Coordinate coordinate, VolleyResponseListener responseListener) {

        String url = getCityWeatherURL(coordinate.getLatitude(), coordinate.getLongitude());
        Util.log(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        WeatherModel weather = extractDataFromJsonObjectToWeatherModel(response);
                        responseListener.onResponse(weather);
                    } catch (Exception e) {
                        responseListener.onError(String.valueOf(e));
                    }
                },
                error -> responseListener.onError(error.toString())
        );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private WeatherModel extractDataFromJsonObjectToWeatherModel(JSONObject response) {

        try {
            JSONObject daily = response.getJSONObject("daily");
            float tempMax = (float) daily.getJSONArray("temperature_2m_max").getDouble(0);
            float tempMin = (float) daily.getJSONArray("temperature_2m_min").getDouble(0);
            float uv_index_max = (float) daily.getJSONArray("uv_index_max").getDouble(0);
            float rain_sum = (float) daily.getJSONArray("rain_sum").getDouble(0);
            return new WeatherModel(tempMax,tempMin,uv_index_max,rain_sum);
        } catch (JSONException e) {
            e.printStackTrace();
            return new WeatherModel(0,0,0,0);
        }
    }

    private static String getCityWeatherURL(double latitude, double longitude) {
        String queryParams = String.format(
                Locale.US,
                "?latitude=%.2f&longitude=%.2f&daily=temperature_2m_max,temperature_2m_min,uv_index_max,rain_sum&timezone=Europe%%2FBerlin",
                latitude,
                longitude
        );

        return BASE_URL + queryParams;
    }

}
