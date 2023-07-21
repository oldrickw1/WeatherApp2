package com.example.weatherservice2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WeatherDataService {
    private static final String QUERY_FOR_CITY_COORDINATES = "https://api.api-ninjas.com/v1/city?name=";
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
                        e.printStackTrace();
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

}
