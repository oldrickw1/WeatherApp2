package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity {
    private Button getCityLocationBtn, useCityLocationBtn, useCityNameBtn;
    private EditText cityInputET, latitudeET, longitudeET;
    private RecyclerView weatherResultRV;
    private float latitude;
    private float longitude;
    private float uv_index_max;

    private RequestQueue requestQueue;


    private WeatherService weatherService = new WeatherService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.log("TEST");
        requestQueue = RequestQueueSingleton.getInstance(this).getRequestQueue();
        setUp();

        getCityLocationBtn.setOnClickListener(v -> {
            float maxUV = weatherService.getMaxUV("Placeholder", requestQueue);
            Util.log("UV: " + maxUV);
        });

    }

    private void setUp() {
        getCityLocationBtn = findViewById(R.id.get_city_geolocation_btn);
        useCityLocationBtn = findViewById(R.id.use_location_btn);
        useCityNameBtn = findViewById(R.id.use_city_name_btn);
        cityInputET = findViewById(R.id.city_input_et);
        latitudeET = findViewById(R.id.latitude_et);
        longitudeET = findViewById(R.id.longitude_et);
        weatherResultRV = findViewById(R.id.weatherRV);

        longitude = 0;
        latitude = 0;
    }
}