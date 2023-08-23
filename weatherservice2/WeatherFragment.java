package com.example.weatherservice2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    public static final String MAX_TEMP = "maxTemp";
    public static final String MIN_TEMP = "minTemp";
    public static final String IMG_URL = "imgURL";
    public static final String UV_INDEX = "uVIndex";
    public static final String RAIN = "rain";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String TEMPS = "temps";

    private static final int MIN_HOUR = 6;
    private static final int MAX_HOUR = 24;

    LineChart lineChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.maxTempTV)).setText("Max temp: " + getArguments().getFloat(MAX_TEMP));
        ((TextView) view.findViewById(R.id.minTempTV)).setText("Min temp: " + getArguments().getFloat(MIN_TEMP));
        ((TextView) view.findViewById(R.id.uVIndexTV)).setText("UV index: " + getArguments().getFloat(UV_INDEX));
        ((TextView) view.findViewById(R.id.rainTV)).setText("Rain: " + getArguments().getFloat(RAIN));
        ((TextView) view.findViewById(R.id.weatherDescriptionTV)).setText(getArguments().getString(DESCRIPTION));
        ((TextView) view.findViewById(R.id.dateTV)).setText(getArguments().getString(DATE));
        setWeathercodeIcon(view, getContext(), getArguments().getString(IMG_URL));
        ArrayList<WeatherModel.TempDP> temps = ((ArrayList<WeatherModel.TempDP>)getArguments().getSerializable(TEMPS));
        lineChart = view.findViewById(R.id.lineChart);
        fillLineChart(temps);
    }

    private void fillLineChart(List<WeatherModel.TempDP> temps) {
        LineDataSet lineDataSet = new LineDataSet(getEntries(temps), "Temperatures");
        formatLineDataSet(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        formatLineChart();
        lineChart.setData(lineData);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineChart.invalidate();
    }

    private List<Entry> getEntries(List<WeatherModel.TempDP> tempDPS) {
        Log.d("OLLIE", "getEntries: " + tempDPS);
        List<Entry> entries = new ArrayList<>();
        for (WeatherModel.TempDP dp : tempDPS.subList(MIN_HOUR, MAX_HOUR+1)) {
            entries.add(new Entry(dp.getTime(), new Float(dp.getTemperature())));
        }
        return entries;
    }


    private void setWeathercodeIcon(View view, Context context, String urlToIcon) {
        try {
            Glide.with(context)
                    .load(urlToIcon)
                    .into((ImageView) view.findViewById(R.id.weatherIcon));
        } catch (Exception e) {
            Log.i("OLLIE", "onCreate: " + e.getMessage());
        }
    }

    private void formatLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleColor(Color.LTGRAY);
        lineDataSet.setCircleRadius(2f);
        lineDataSet.setDrawValues(false);
    }


    private void formatLineChart() {
        // X-axis formatting
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(MIN_HOUR);
        xAxis.setAxisMaximum(MAX_HOUR);
        xAxis.setValueFormatter(new TimeAxisValueFormatter());
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);

        // Y-axis formatting
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(getArguments().getFloat(MIN_TEMP) - 10);
        yAxis.setAxisMaximum(getArguments().getFloat(MAX_TEMP) + 5);
        yAxis.setValueFormatter(new TemperatureAxisValueFormatter()); // Custom formatter for temperature
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.WHITE);

        // Other customizations
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setBackgroundColor(Color.BLACK);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getDescription().setEnabled(false);
    }
}