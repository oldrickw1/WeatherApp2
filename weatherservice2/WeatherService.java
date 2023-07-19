package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class WeatherService extends AppCompatActivity {
    private static final float MADRID_LATITUDE = 40.4168f;
    private static final float MADRID_LONGITUDE = -3.7038f;

    private float uv;

    public float getMaxUV(String location, RequestQueue requestQueue) {
        // Some variables are hard coded for testing.
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + MADRID_LATITUDE + "&longitude=" + MADRID_LONGITUDE + "&daily=uv_index_max&start_date=2023-06-23&end_date=2023-06-30&timezone=Europe%2FBerlin";
        Util.log("URL : " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONObject daily = null;
                    try {
                        daily = response.getJSONObject("daily");
                        float uv = (float) daily.getJSONArray("uv_index_max").getDouble(0);
                        Util.log("UV: " + uv);
                    }
                    catch (Exception e) {
                        Util.log(e.toString());
                    }
                },
                error -> {
                    Util.log(error.toString());
                }
        );
        requestQueue.add(jsonObjectRequest);
        return uv;
    }

}
