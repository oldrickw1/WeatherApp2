package com.example.weatherservice2;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TemperatureAxisValueFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
        String formattedValue = decimalFormat.format(value);
        formattedValue = formattedValue.replace(",", ".");
        float roundedValue = Float.parseFloat(formattedValue);
        return roundedValue + " °C"; // Format temperature label
    }
}
