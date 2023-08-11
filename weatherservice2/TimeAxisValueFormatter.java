package com.example.weatherservice2;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class TimeAxisValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        int hour = (int) value;
        return String.format("%02d:00", hour);
    }
}
