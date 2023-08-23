package com.example.weatherservice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DisplayWeatherActivity extends AppCompatActivity {
    private TextView cityNameDisplayTV;
    private TextView geoLocationTV;

    private final WeatherDataServiceAPI weatherAPI = new WeatherDataServiceAPI(this);
    private TextView countryNameTV;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private LineChart weeklyChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);

        getWeather(getCityData());
    }

    private CityData getCityData() {
        Bundle extras = getIntent().getExtras();
        String cityName = extras.getString("cityName");
        String country = extras.getString("country");
        double latitude = extras.getDouble("latitude",-1);
        double longitude = extras.getDouble("longitude",-1);
        return new CityData(cityName,country,latitude,longitude);
    }

    private void getWeather(CityData city) {
        weatherAPI.getCityWeatherForWeek(city).thenAccept(results -> {
            Log.i("RESULT", "getWeather: result: " + results.toString());
            setContentView(R.layout.activity_weather_info_display);
            countryNameTV = findViewById(R.id.countryNameTV);
            geoLocationTV = findViewById(R.id.geoLocation);
            cityNameDisplayTV = findViewById(R.id.cityNameTV);
            tabLayout = findViewById(R.id.tabLayout);
            viewPager2 = findViewById(R.id.pager);

            cityNameDisplayTV.setText(city.getCityName());
            countryNameTV.setText(city.getCountry());
            geoLocationTV.setText("(" +   city.getLatitude() + " , " + city.getLongitude() + ")");

            drawMinAndMaxWeekGraph(extractTemperatureEntries(results, false), extractTemperatureEntries(results, true), getDateLabels(results));

            WeatherFragmentStateAdapter weatherFragmentStateAdapter = new WeatherFragmentStateAdapter(this, getSupportFragmentManager(), getLifecycle(), results);
            viewPager2.setAdapter(weatherFragmentStateAdapter);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(results.get(position).getDate().substring(0,2))).attach();
        });
    }

    private String[] getDateLabels(ArrayList<WeatherModel> results) {
        String[] days = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            days[i] = results.get(i).getDate().substring(0,2);
        }
        return days;
    }

    private List<Entry> extractTemperatureEntries(ArrayList<WeatherModel> results, boolean isMax) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            double temperature = isMax ? results.get(i).getTempMax() : results.get(i).getTempMin();
            entries.add(new Entry(i, (float) temperature));
        }
        return entries;
    }

    private void drawMinAndMaxWeekGraph(List<Entry> minTemps, List<Entry> maxTemps,String[] dayLabels) {

        weeklyChart = findViewById(R.id.weeklyChart);
        LineDataSet minTempDS = new LineDataSet(minTemps, "Min. temperatures");
        LineDataSet maxTempDS = new LineDataSet(maxTemps, "Max. temperatures");
        minTempDS.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        maxTempDS.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Apply custom styling to the data sets
        minTempDS.setColor(Color.parseColor(getString(R.string.minTempColorBasic)));
        minTempDS.setLineWidth(2f);
        minTempDS.setCircleColor(Color.parseColor(getString(R.string.minTempColorAccent)));
        minTempDS.setCircleRadius(2f);
        minTempDS.setDrawValues(false);

        maxTempDS.setColor(Color.parseColor(getString(R.string.maxTempColorBasic)));
        maxTempDS.setLineWidth(2f);
        maxTempDS.setCircleColor(Color.parseColor(getString(R.string.maxTempColorAccent)));
        maxTempDS.setCircleRadius(2f);
        maxTempDS.setDrawValues(false);


        // Customize the chart appearance
        weeklyChart.setTouchEnabled(false);
        weeklyChart.setDragEnabled(false);
        weeklyChart.setScaleEnabled(false);
        weeklyChart.setPinchZoom(false);
        weeklyChart.setDoubleTapToZoomEnabled(false);
        weeklyChart.getDescription().setEnabled(false); // Disable description

        // Customize the X-axis
        XAxis xAxis = weeklyChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayLabels));
        xAxis.setTextSize(9f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        // Customize the Y-axis
        YAxis yAxisLeft = weeklyChart.getAxisLeft();
        yAxisLeft.setValueFormatter(new TemperatureAxisValueFormatter());
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(7f);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setLabelCount(4, true);

        YAxis yAxisRight = weeklyChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Set data and invalidate the chart to apply changes
        LineData lineData = new LineData();
        lineData.addDataSet(minTempDS);
        lineData.addDataSet(maxTempDS);

        List<LegendEntry> legendEntries = new ArrayList<>();
        legendEntries.add(new LegendEntry("max", Legend.LegendForm.LINE, 10f, 2f, null, Color.parseColor(getString(R.string.maxTempColorBasic))));
        legendEntries.add(new LegendEntry("min", Legend.LegendForm.LINE, 10f, 2f, null, Color.parseColor(getString(R.string.minTempColorBasic))));
        Legend legend = weeklyChart.getLegend();
        legend.setCustom(legendEntries);
        legend.setTextColor(Color.WHITE);
        weeklyChart.setData(lineData);
        weeklyChart.invalidate();
    }
}
